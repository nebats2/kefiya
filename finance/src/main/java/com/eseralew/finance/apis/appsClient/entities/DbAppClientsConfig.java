package com.kefiya.home.apis.appsClient.entities;

import com.kefiya.home.configs.models.DatabaseListModel;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
        basePackages = "com.kefiya.home.apis.appsClient",
        entityManagerFactoryRef = "appClientEntityManagerFactory",
        transactionManagerRef = "appClientTransactionManager"
)
@AllArgsConstructor
public class DbAppClientsConfig  {

    private final DatabaseListModel databaseListModel;
    final String ENTITY_PACKAGE ="com.kefiya.home.apis.appsClient";


    @Bean(name="appClientEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean appClientEntityManagerFactory() {
        var database = databaseListModel.getApClientDb();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(appClientDatasource());
        em.setPackagesToScan(ENTITY_PACKAGE); // set your package

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", database.getDialect());

        em.setJpaProperties(properties);

        return em;
    }
    @Bean(name="appClientDatasource")
    public DataSource appClientDatasource() {
        var database = databaseListModel.getApClientDb();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(database.getDriver());
        dataSource.setUrl(database.getUrl());
        dataSource.setPassword(database.getPassword());
        dataSource.setUsername(database.getUsername());

        return dataSource;
    }


    @Bean(name = "appClientTransactionManager")
    public PlatformTransactionManager appClientTransactionManager(
            @Qualifier("appClientEntityManagerFactory") EntityManagerFactory appClientEntityManagerFactory) {
        return new JpaTransactionManager(appClientEntityManagerFactory);
    }
}
