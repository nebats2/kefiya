package com.kefiya.home.apis.staffApi.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="staff_role")
@Data
public class StaffRoleEntity {
    @ Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    StaffEntity staff;

    String role;

    Boolean enabled;
}
