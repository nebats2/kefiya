package com.kefiya.home.configs;

import com.kefiya.home.configs.models.DatabaseListModel;
import com.kefiya.home.configs.models.DatabaseModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "database")
@Data
public class DbConfigConstants   {

    String user_db;
    String notification_db;
    String staff_db;
    String app_clients_db;
    String map_db;
    String profile_db;
    String photo_db;
    String finance_db;

    String domain;
    String jdbc;
    int port;
    String username;
    String password;
    String dialect;
    String driver;

    @Bean
    public DatabaseListModel databaseUrlModel() {
        var model = new DatabaseListModel();

        model.setUserDb(buildWithDatabaseName(user_db));
        model.setNotificationDb(buildWithDatabaseName(notification_db));
        model.setApClientDb(buildWithDatabaseName(app_clients_db));
        model.setStaffDb(buildWithDatabaseName(staff_db));
        model.setProfileDb(buildWithDatabaseName(profile_db));
        model.setFinanceDb(buildWithDatabaseName(finance_db));


        return model;

    }
    private DatabaseModel buildWithDatabaseName( String databaseName){
        var url = jdbc.concat(domain).concat(":").concat(String.valueOf(port)).concat("/"+databaseName);
        return build(url);
    }
    private DatabaseModel build(String url){
        var database = new DatabaseModel();
        database.setUrl(url);
        database.setUsername(username);
        database.setPassword(password);
        database.setDialect(dialect);
        database.setDriver(driver);
        return database;
    }
}
