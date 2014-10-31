/*
 * Copyright (c) 2012, vsc-technologies - www.voyages-sncf.com
 * All rights reserved.
 * 
 * Les presents codes sources sont proteges par le droit d'auteur et 
 * sont la propriete exclusive de VSC Technologies.
 * Toute representation, reproduction, utilisation, exploitation, modification, 
 * adaptation de ces codes sources sont strictement interdits en dehors 
 * des autorisations formulees expressement par VSC Technologies, 
 * sous peine de poursuites penales. 
 * 
 * Usage of this software, in source or binary form, partly or in full, and of
 * any application developed with this software, is restricted to the
 * customer.s employees in accordance with the terms of the agreement signed
 * with VSC-technologies.
 */
package quicky.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import quicky.model.Gare;
import quicky.model.GareRaw;
import quicky.model.Hotel;
import quicky.model.HotelRaw;
import quicky.processor.GareItemProcessor;
import quicky.processor.HotelItemProcessor;
import quicky.tasklet.IndexCreationTasklet;
import quicky.writer.IndexGareWriter;
import quicky.writer.IndexHotelWriter;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<GareRaw> gareReader() {
        FlatFileItemReader<GareRaw> reader = new FlatFileItemReader<GareRaw>();
        reader.setResource(new ClassPathResource("gares-data.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<GareRaw>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"nom", "pays", "ville", "latitude", "longitude", "tgv"});
                setDelimiter(";");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<GareRaw>() {{
                setTargetType(GareRaw.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<GareRaw, Gare> gareProcessor() {
        return new GareItemProcessor();
    }

    @Bean
    public ItemWriter<Gare> gareWriter() {
        return new IndexGareWriter();
    }

    @Bean
    public ItemReader<HotelRaw> hotelReader() {
        FlatFileItemReader<HotelRaw> reader = new FlatFileItemReader<HotelRaw>();
        reader.setResource(new ClassPathResource("hotels-data.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<HotelRaw>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"id", "nom", "ville", "codePostal", "pays", "boost", "residence", "theme1", "theme2", "visuel", "latitude", "longitude", "etoiles", "description"});
                setDelimiter(";");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<HotelRaw>() {{
                setTargetType(HotelRaw.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<HotelRaw, Hotel> hotelProcessor() {
        return new HotelItemProcessor();
    }

    @Bean
    public ItemWriter<Hotel> hotelWriter() {
        return new IndexHotelWriter();
    }


    @Bean
    public Step init_Step1() {
        return stepBuilderFactory.get("init_Step1")
                .tasklet(new IndexCreationTasklet()).build();
    }

    @Bean
    public Step gare_Step2(ItemReader<GareRaw> gareReader, ItemWriter<Gare> gareWriter, ItemProcessor<GareRaw,
            Gare> gareProcessor) {
        return stepBuilderFactory.get("gare_Step2")
                .<GareRaw, Gare>chunk(1000)
                .reader(gareReader)
                .processor(gareProcessor)
                .writer(gareWriter)
                .build();
    }

    @Bean
    public Step hotel_Step3(ItemReader<HotelRaw> hotelReader, ItemWriter<Hotel> hotelWriter, ItemProcessor<HotelRaw, Hotel> hotelProcessor) {
        return stepBuilderFactory.get("hotel_Step3")
                .<HotelRaw, Hotel>chunk(1000)
                .reader(hotelReader)
                .processor(hotelProcessor)
                .writer(hotelWriter)
                .build();
    }

    @Bean
    public Job mainJob(Step init_Step1, Step gare_Step2, Step hotel_Step3) throws Exception {
        return jobBuilderFactory.get("mainJob")
                .incrementer(new RunIdIncrementer())
                .start(init_Step1)
                .next(gare_Step2)
                .next(hotel_Step3)
                .build();
    }
}
