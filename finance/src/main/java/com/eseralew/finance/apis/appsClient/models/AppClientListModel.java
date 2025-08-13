package com.kefiya.home.apis.appsClient.models;

import com.kefiya.home.apis.appsClient.entities.AppClientEntity;
import lombok.Data;

import java.util.List;

@Data
public class AppClientListModel {
    List<AppClientEntity> appClients;
}
