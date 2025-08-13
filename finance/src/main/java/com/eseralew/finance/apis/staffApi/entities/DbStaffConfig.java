package com.kefiya.home.apis.staffApi.entities;




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
        basePackages = "com.kefiya.home.apis.staffApi.entities",
        entityManagerFactoryRef = "staffEntityManagerFactory",
        transactionManagerRef = "staffTransactionManager"
)
@AllArgsConstructor
public class DbStaffConfig {
    final String ENTITY_PACKAGE ="com.kefiya.home.apis.staffApi.entities";

    private final DatabaseListModel databaseList;


    @Bean(name="staffEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean staffEntityManagerFactory() {
        var database = databaseList.getStaffDb();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(staffDatasource());
        em.setPackagesToScan(ENTITY_PACKAGE); // set your package

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
       // properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", database.getDialect());

        em.setJpaProperties(properties);

        return em;
    }
    @Bean(name="staffDatasource")
    public DataSource staffDatasource() {
        var staffDb = databaseList.getStaffDb();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(staffDb.getDriver());
        dataSource.setUrl(staffDb.getUrl());
        dataSource.setUsername(staffDb.getUsername());
        dataSource.setPassword(staffDb.getPassword());

        return dataSource;
    }


    @Bean(name = "staffTransactionManager")
    public PlatformTransactionManager staffTransactionManager(
            @Qualifier("staffEntityManagerFactory") EntityManagerFactory staffEntityManagerFactory) {
        return new JpaTransactionManager(staffEntityManagerFactory);
    }
}
