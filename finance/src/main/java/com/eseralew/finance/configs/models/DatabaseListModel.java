package com.kefiya.home.configs.models;

import lombok.Data;

@Data
public class DatabaseListModel {
     DatabaseModel userDb;
     DatabaseModel notificationDb;
     DatabaseModel apClientDb;
     DatabaseModel staffDb;
     DatabaseModel profileDb;
     DatabaseModel financeDb;
}
