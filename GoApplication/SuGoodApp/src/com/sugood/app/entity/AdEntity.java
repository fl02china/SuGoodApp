package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/4/14 17:01
 * ganweib@gmail.com
 * describe:
 */

public class AdEntity implements Serializable {
    private static final long serialVersionUID = 7331467361270894518L;
    String id;
    String type;
    String photo;
    String diyid;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDiyid() {
        return diyid;
    }

    public void setDiyid(String diyid) {
        this.diyid = diyid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AdEntity{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", photo='" + photo + '\'' +
                ", diyid='" + diyid + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
