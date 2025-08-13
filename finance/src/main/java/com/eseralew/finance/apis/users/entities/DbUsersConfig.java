package com.kefiya.home.apis.users.entities;

import com.kefiya.home.configs.models.DatabaseListModel;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.kefiya.home.apis.users.entities",
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager"
)
@AllArgsConstructor
public class DbUsersConfig  {

    private final String ENTITY_PACKAGE ="com.kefiya.home.apis.users.entities";
    private final DatabaseListModel databaseListModel;



    @Bean(name="userDatasource")
    public DataSource userDataSource() {
        var userDatabase =  databaseListModel.getUserDb();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(userDatabase.getUrl());
        dataSource.setDriverClassName(userDatabase.getDriver());
        dataSource.setPassword(userDatabase.getPassword());
        dataSource.setUsername(userDatabase.getUsername());

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory() {
        var userDatabase =  databaseListModel.getUserDb();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource());
        em.setPackagesToScan(ENTITY_PACKAGE); // set your package

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", userDatabase.getDialect());
        properties.setProperty("hibernate.show_sql", "false"); // Optional
        properties.setProperty("hibernate.format_sql", "false"); // Optional
        em.setJpaProperties(properties);

        return em;
    }

    @Bean(name = "userTransactionManager")
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
