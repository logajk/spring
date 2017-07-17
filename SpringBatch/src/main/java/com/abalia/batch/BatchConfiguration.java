package com.abalia.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.abalia.listener.JobCompletionNotificationListener;
import com.abalia.listener.PersonaDTOListener;
import com.abalia.model.Persona;
import com.abalia.model.PersonaDTO;
import com.abalia.processor.PersonaDTOItemProcessor;
import com.abalia.processor.PersonaItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	public DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Persona> reader(){
		FlatFileItemReader<Persona> reader = new FlatFileItemReader<>();
		
		reader.setResource(new ClassPathResource("sample-data.csv"));
		reader.setLineMapper(new DefaultLineMapper<Persona>(){{
			setLineTokenizer(new DelimitedLineTokenizer(){{
				setNames(new String[]{"nombre","apellidos"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Persona>(){{
				setTargetType(Persona.class);
			}});
		}});
		return reader;
	}
	
	@Bean
	public FlatFileItemReader<PersonaDTO> readerDTO(){
		FlatFileItemReader<PersonaDTO> reader = new FlatFileItemReader<>();
		
		reader.setResource(new ClassPathResource("sample-data-dto.csv"));
		reader.setLineMapper(new DefaultLineMapper<PersonaDTO>(){{
			setLineTokenizer(new DelimitedLineTokenizer(){{
				setNames(new String[]{"nombre","apellidos", "fecha"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<PersonaDTO>(){{
				setTargetType(PersonaDTO.class);
			}});
		}});
		return reader;
	}
	
	@Bean
	public PersonaItemProcessor processor(){
		return new PersonaItemProcessor();
	}
	
	@Bean
	public PersonaDTOItemProcessor processorDTO(){
		return new PersonaDTOItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Persona> writter(){
		JdbcBatchItemWriter<Persona> writter = new JdbcBatchItemWriter<Persona>();
		writter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Persona>());
		writter.setSql("INSERT INTO persona (nombre, apellidos) VALUES (:nombre, :apellidos)");
		writter.setDataSource(dataSource);
		return writter;
	}
	
	@Bean
	public JdbcBatchItemWriter<PersonaDTO> writterDTO(){
		JdbcBatchItemWriter<PersonaDTO> writter = new JdbcBatchItemWriter<PersonaDTO>();
		writter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PersonaDTO>());
		writter.setSql("INSERT INTO persona (nombre, apellidos, fecha) VALUES (:nombre, :apellidos, :fecha)");
		writter.setDataSource(dataSource);
		return writter;
	}
	
	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener){
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1())
				.end()
				.build();
	}
	
	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
				.<Persona, Persona> chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writter())
				.build();
	}
	
	@Bean
	public Job importUserJobDTO(PersonaDTOListener listener){
		return jobBuilderFactory.get("importUserJobDTO")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(stepDTO())
				.end()
				.build();
	}
	
	@Bean
	public Step stepDTO(){
		return stepBuilderFactory.get("stepDTO")
				.<PersonaDTO, PersonaDTO>chunk(10)
				.reader(readerDTO())
				.processor(processorDTO())
				.writer(writterDTO())
				.build();
	}
}
