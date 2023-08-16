package com.example.SpringH2.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Data
@Table(name = "Users")
/**
 * this class represents our table and table's column
 */
public class User {

    @Id
    @Column(name = "USERNAME")
    private String username ;

    @Column(name = "BIRTHDATE")
    private String birthdate ;

    @Column(name = "COUNTRY")
    private String country ;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "GENDER")
    private String gender;


}
