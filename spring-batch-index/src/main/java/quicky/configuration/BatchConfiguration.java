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
import quicky.tasklet.IndexValidateTasklet;
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

    @Autowired
    private IndexCreationTasklet indexCreationTasklet;

    @Autowired
    private IndexValidateTasklet indexValidateTasklet;


    @Bean
    public ItemReader<GareRaw> gareReader() {
        FlatFileItemReader<GareRaw> reader = new FlatFileItemReader<GareRaw>();
        reader.setEncoding("UTF-8");
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
        reader.setEncoding("UTF-8");
        reader.setResource(new ClassPathResource("hotels-data-complete.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<HotelRaw>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"id", "nom", "ville", "codePostal", "boost", "residence", "theme1", "theme2", "visuel", "latitude",
                        "longitude", "pays", "etoiles", "description"});
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
                .tasklet(indexCreationTasklet).build();
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
                .<HotelRaw, Hotel>chunk(10000)
                .reader(hotelReader)
                .processor(hotelProcessor)
                .writer(hotelWriter)
                .build();
    }

    @Bean
    public Step validate_Step4() {
        return stepBuilderFactory.get("validate_Step4")
                .tasklet(indexValidateTasklet).build();
    }

    @Bean
    public Job mainJob(Step init_Step1, Step gare_Step2, Step hotel_Step3, Step validate_Step4) throws Exception {
        return jobBuilderFactory.get("mainJob")
                .incrementer(new RunIdIncrementer())
                .start(init_Step1)
                .next(gare_Step2)
                .next(hotel_Step3)
                .next(validate_Step4)
                .build();
    }
}
