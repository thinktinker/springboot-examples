package com.sctp.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="product")
public class Product {

    @Id
    @Column(name="id")
    @NotBlank(message = "Product id cannot be blank.")
    private String id;
    @Column(name="name")
    @NotBlank(message = "Product name cannot be blank.")
    private String name;

    public Product(){

    }

    public Product(String id, String name){
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
