package com.avinash.popularmoviesproject.pojo;

/**
 * Created by AVINASH on 16-05-2017.
 */

public class Trailer {

    String key;
    String site;
    String type;
    String name;

    public Trailer() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return key+"\n"+name+"\n"+type+"\n"+site+"\n\n";
    }
}
