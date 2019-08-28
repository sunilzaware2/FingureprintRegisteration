package com.Sunilmantra.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String user_name="";

    @ColumnInfo(name = "user_authentication",typeAffinity = ColumnInfo.BLOB)
    private byte[] user_authentication;

    public User(String user_name,byte[] user_authentication) {
        this.user_name = user_name;
        this.user_authentication = user_authentication;
    }

    public User() {}


    public byte[] getUser_authentication() {
        return user_authentication;
    }

    public void setUser_authentication(byte[] user_authentication) {
        this.user_authentication = user_authentication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
