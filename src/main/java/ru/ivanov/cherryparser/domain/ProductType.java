package ru.ivanov.cherryparser.domain;

import javax.persistence.*;

@Entity
public class ProductType {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String filename;

    public ProductType() {
    }

    public ProductType(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String content) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}