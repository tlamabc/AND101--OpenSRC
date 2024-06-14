package com.example.asm_coban.model;

import java.io.Serializable;

public class Staff implements Serializable {
    private String id;
    private String name;
    private String room;
    private int image;

    public Staff(String id, String name, String room, int image) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.image = image;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
