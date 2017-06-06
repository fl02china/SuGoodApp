package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wilk on 17/3/14 15:42
 * ganweib@gmail.com
 * describe:
 */

public class Tuan implements Serializable {
    private static final long serialVersionUID = 645857062815407348L;

    private String title;
    private String instructions;
    private String details;
    private String Price;
    private String tuanId;
    private String tuanPrice;
    private List<String> thumb;
    private String photo;
    private String intro;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return Double.parseDouble(Price) / 100 + "";
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTuanId() {
        return tuanId;
    }

    public void setTuanId(String tuanId) {
        this.tuanId = tuanId;
    }

    public String getTuanPrice() {
        return tuanPrice;
    }

    public void setTuanPrice(String tuanPrice) {
        this.tuanPrice = Double.parseDouble(tuanPrice) / 100 + "";
    }

    public List<String> getThumb() {
        return thumb;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public String toString() {
        return "Tuan{" +
                "title='" + title + '\'' +
                ", instructions='" + instructions + '\'' +
                ", details='" + details + '\'' +
                ", Price='" + Price + '\'' +
                ", tuanId='" + tuanId + '\'' +
                ", tuanPrice='" + tuanPrice + '\'' +
                ", thumb=" + thumb +
                ", photo='" + photo + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }
}
