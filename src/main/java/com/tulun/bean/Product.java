package com.tulun.bean;

import java.io.Serializable;

/**
 * @author 阿劼
 */
public class Product implements Serializable {
    private static final long serialVersionUID = -8118726338766941248L;
    private Integer id;

    private String name;

    private Double price;

    private Integer number;

    private String extra;

    public Product(){}

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", extra='" + extra + '\'' +
                '}';
    }

    public Product(Integer id, String name, Double price, Integer number, String extra) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.number = number;
        this.extra = extra;
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

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}