package com.android.zj.ai.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "phone_data".
 */
@Entity
public class PhoneData {

    @Id(autoincrement = true)
    private Long id;
    private String height;//" : "960",
    private String width;//" : "540",
    private String keyword;//" : "G22+X715E+RUBY\/AMAZE 4G",
    private String count;//" : "",
    private String brand;//" : "HTC",
    private String version;//" : "2.3",
    private String tac;//" : "35726504",
    private String name;//" : "HTC X715e（Amaze 4G\/G22）",


    @Generated
    public PhoneData() {
    }

    public PhoneData(Long id) {
        this.id = id;
    }

    @Generated
    public PhoneData(
                     String height,
                     String width,
                     String keyword,
                     String count,
                     String brand,
                     String version,
                     String tac,
                     String name) {
        this.height = height;
        this.width = width;
        this.keyword = keyword;
        this.count = count;
        this.brand = brand;
        this.version = version;
        this.tac = tac;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}