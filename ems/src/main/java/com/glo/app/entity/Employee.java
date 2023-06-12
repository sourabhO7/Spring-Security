package com.glo.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name",length = 60,nullable = false)
    private String firstName;
    @Column(name = "last_name",length = 60,nullable = false)
    private String lastName;
    @Column(name = "email",length = 180,nullable = false,unique = true)
    private String email;
    @Column(name = "department",length = 60,nullable = false,unique = true)
    private String department;
    @Column(name = "position",length = 60,nullable = false)
    private String position;
    @Column(name = "salary",length = 60,nullable = false)
    private long salary;
}
