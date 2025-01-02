package com.example.spring.beginnerbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.spring.beginnerbatch.common.Constants;
import com.example.spring.beginnerbatch.domain.RawSensorDataLineMapper;
import com.example.spring.beginnerbatch.dto.DailySensorData;

/**
 * Classe de configuration principale pour le batch TemperatureSensor
 */
@Configuration
@PropertySource("classpath:application.properties")
public class TemperatureSensorRootConfiguration extends DefaultBatchConfiguration {

	@Value(Constants.RAW_SENSORDATA_FILE)
	private Resource rawDailyInputResource;

	/**
	 * Job de lecture des données des capteurs avec aggrégation de leurs valeurs
	 * sous forme de min/max/moyenne, export au format XML standard et export
	 * des anomalies dans un fichier csv distinct
	 * 
	 * @param jobRepository
	 * @param aggregateSensorStep
	 * @return le job
	 */
	@Bean
	public Job temperatureSensorJob(JobRepository jobRepository,
			@Qualifier(Constants.AGGREGATE_SENSORS_STEP) Step aggregateSensorStep) {
		return new JobBuilder("temperatureSensorJob", jobRepository)
				.start(aggregateSensorStep)
				.build();
	}
	
	/**
	 * Step de lecture des données des capteurs avec aggrégation de leurs valeurs
	 * sous forme de min, max, moyenne et export dans le format XML standard
	 * 
	 * @param jobRepository
	 * @param transactionManager
	 * @return le step
	 */
	@Bean
	@Qualifier(Constants.AGGREGATE_SENSORS_STEP)
	public Step aggregateSensorStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		ListItemWriter<Object> listWriter = new ListItemWriter<Object>();
		
		return new StepBuilder("aggregate-sensor", jobRepository)
				// Lecture item par item
				.chunk(1, transactionManager)
				.reader(new FlatFileItemReaderBuilder<DailySensorData>()
						.name("dailySensorDataReader")
						.resource(rawDailyInputResource)
						.lineMapper(new RawSensorDataLineMapper())
						.build())
				.writer(listWriter)
				.listener(new StepExecutionListener() {
					@Override
				    public ExitStatus afterStep(StepExecution stepExecution) {
				        var writtenItems = listWriter.getWrittenItems();
				        System.out.println("Items écrits : " + writtenItems);
				        return ExitStatus.COMPLETED; // Retourne un statut valide
				    }
				})
				.build();
	}
	
    /* ******************************** Définition des beans nécessaires à Spring Batch ********************************** */

    /**
     * A cause de l'utilisation de {@link DefaultBatchConfiguration}, la dataSource doit être créée explicitement.
     * Quand {@link org.springframework.batch.core.configuration.annotation.EnableBatchProcessing} est utilisé,
     * la datasource est créée automatiquement par Spring batch
     * 
     * DataSource simple seulement pour faire des tests (pas de pool).
     * Pour la production si container JakartaEE, utiliser le pool du container via JNDI
     * ou utiliser le pool Hikari
     */
	@Bean
	public DataSource dataSource(@Value("${spring.datasource.driver-class-name}") String driverClassName,
			@Value("${spring.datasource.url}") String url,
			@Value("${spring.datasource.username}") String userName,
			@Value("${spring.datasource.password}") String password) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		
		return dataSource;
	}
	
	// Transaction manager le plus simple possible pour permettre à Spring Batch de stocker
	// les informations de complétion des jobs et steps
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		JdbcTransactionManager transactionManager = new JdbcTransactionManager();
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}

    /**
     * Du fait de l'utilisation de {@link DefaultBatchConfiguration}, il faut fournir un database
     * initializer pour que Spring Batch initialise le schéma de la base de données lors de la
     * première utilisation. Si on avait utilisé {@link org.springframework.batch.core.configuration.annotation.EnableBatchProcessing}
     * ce comportement aurait été contrôlé directement par la propriété 'spring.batch.initialize-schema'
     */
	@Bean
	public BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(DataSource dataSource,
			BatchProperties batchProperties) {
		return new BatchDataSourceScriptDatabaseInitializer(dataSource, batchProperties.getJdbc());
	}
	
	/**
     * Du fait de l'utilisation de {@link DefaultBatchConfiguration}, nous devons explicitement définir
     * le mode d'initialisation du schéma de la base de données. Si nous avions utilisé {@link org.springframework.batch.core.configuration.annotation.EnableBatchProcessing},
     * la définition de la propriété 'spring.batch.initialize-schema' aurait été suffisante
     */
	@Bean
	public BatchProperties batchProperties(@Value("${spring.batch.initialize-schema}") String databaseInitializationMode) {
		BatchProperties batchProperties = new BatchProperties();
		batchProperties.getJdbc().setInitializeSchema(
				DatabaseInitializationMode.valueOf(databaseInitializationMode.toUpperCase())
				);
		return batchProperties;
	}
	
}
