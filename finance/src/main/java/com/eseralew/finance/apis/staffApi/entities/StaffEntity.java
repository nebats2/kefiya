package com.kefiya.home.apis.staffApi.entities;

import com.kefiya.home.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="staffs")
@Data
public class StaffEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;
    String staffId;
    String firstName;
    String lastName;
    String password;

    @Column(nullable = false)
    Boolean active;


    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<StaffRoleEntity> roles = new ArrayList<>();
}
