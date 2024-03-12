package com.example.structuremonitoringsystem.RecyclerView.Item;

public class ObjectItem {
    String width;
    String height;
    String thickness;
    String tempName;
    String id;
    public ObjectItem(String width, String height, String thickness, String tempName, String id) {
        this.width = width;
        this.height = height;
        this.thickness = thickness;
        this.tempName = tempName;
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }
}
