package com.kefiya.home.configs.models;

import lombok.Data;

@Data
public class DatabaseModel {
    String url;
    String username;
    String password;
    String dialect;
    String driver;
}
