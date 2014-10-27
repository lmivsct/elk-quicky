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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public ItemReader<Gare> reader() {
        FlatFileItemReader<Gare> reader = new FlatFileItemReader<Gare>();
        reader.setResource(new ClassPathResource("gares-data.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Gare>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"nom", "pays", "ville", "latitude", "longitude", "tgv"});
                setDelimiter(";");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Gare>() {{
                setTargetType(Gare.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Gare, Gare> processor() {
        return new GareItemProcessor();
    }

    @Bean
    public ItemWriter<Gare> writer() {
        return new IndexWriter();
    }

    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer()).flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Gare> reader, ItemWriter<Gare> writer, ItemProcessor<Gare, Gare> processor) {
        return stepBuilderFactory.get("step1")
                .<Gare, Gare>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer).build();
    }
    
}