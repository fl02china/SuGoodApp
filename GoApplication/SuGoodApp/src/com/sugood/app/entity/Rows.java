package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/4/25 18:01
 * ganweib@gmail.com
 * describe:
 */

public class Rows implements Serializable {
    private static final long serialVersionUID = -3848948214218598553L;
    private String id;
    private Cell cell;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "Rows{" +
                "id='" + id + '\'' +
                ", cell=" + cell +
                '}';
    }
}
