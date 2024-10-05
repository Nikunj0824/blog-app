package com.personal.blog_app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    private int id;

    @Column(name = "role_name")
    private String name;
}
