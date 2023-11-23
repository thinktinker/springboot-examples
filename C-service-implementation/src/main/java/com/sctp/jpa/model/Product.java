package com.sctp.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="product")
public class Product {

    @Id
    @Column(name="id")
    private String id;
    @Column(name="name")
    private String name;

    public void Product(){

    }

    public void Product(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
