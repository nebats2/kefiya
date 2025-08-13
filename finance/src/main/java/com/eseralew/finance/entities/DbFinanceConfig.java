package com.kefiya.home.entities;

import com.kefiya.home.configs.models.DatabaseListModel;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.kefiya.home.entities",
        entityManagerFactoryRef = "financeEntityManagerFactory",
        transactionManagerRef = "financeTransactionManager"
)
@AllArgsConstructor
public class DbFinanceConfig {

    private final String ENTITY_PACKAGE ="com.kefiya.home.entities";
    private final DatabaseListModel databaseListModel;


    @Bean(name ="financeDataSource")
    @Primary
    public DataSource financeDataSource() {
        var photoDb =  databaseListModel.getFinanceDb();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(photoDb.getUrl());
        dataSource.setDriverClassName(photoDb.getDriver());
        dataSource.setPassword(photoDb.getPassword());
        dataSource.setUsername(photoDb.getUsername());

        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean financeEntityManagerFactory() {
        var photoDb =  databaseListModel.getFinanceDb();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(financeDataSource());
        em.setPackagesToScan(ENTITY_PACKAGE); // set your package

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", photoDb.getDialect());
        properties.setProperty("hibernate.show_sql", "false"); // Optional
        properties.setProperty("hibernate.format_sql", "false"); // Optional
        em.setJpaProperties(properties);

        return em;
    }
    @Primary
    @Bean(name = "financeTransactionManager")
    public PlatformTransactionManager financeTransactionManager(
            @Qualifier("financeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
