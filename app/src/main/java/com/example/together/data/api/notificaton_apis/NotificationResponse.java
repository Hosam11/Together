package com.example.together.data.api.notificaton_apis;

import com.example.together.data.model.Notification;

import java.util.ArrayList;

public class NotificationResponse  {
    ArrayList<Notification> data = new ArrayList < > ();
    Links LinksObject;
    Meta MetaObject;

    public ArrayList<Notification> getData() {
        return data;
    }

    public void setData(ArrayList<Notification> data) {
        this.data = data;
    }

// Getter Methods

    public Links getLinks() {
        return LinksObject;
    }

    public Meta getMeta() {
        return MetaObject;
    }

    // Setter Methods

    public void setLinks(Links linksObject) {
        this.LinksObject = linksObject;
    }

    public void setMeta(Meta metaObject) {
        this.MetaObject = metaObject;
    }
}
class Meta {
    private float current_page;
    private float from;
    private float last_page;
    private String path;
    private float per_page;
    private float to;
    private float total;


    // Getter Methods

    public float getCurrent_page() {
        return current_page;
    }

    public float getFrom() {
        return from;
    }

    public float getLast_page() {
        return last_page;
    }

    public String getPath() {
        return path;
    }

    public float getPer_page() {
        return per_page;
    }

    public float getTo() {
        return to;
    }

    public float getTotal() {
        return total;
    }

    // Setter Methods

    public void setCurrent_page(float current_page) {
        this.current_page = current_page;
    }

    public void setFrom(float from) {
        this.from = from;
    }

    public void setLast_page(float last_page) {
        this.last_page = last_page;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPer_page(float per_page) {
        this.per_page = per_page;
    }

    public void setTo(float to) {
        this.to = to;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
 class Links {
    private String first;
    private String last;
    private String prev = null;
    private String next = null;


    // Getter Methods

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getPrev() {
        return prev;
    }

    public String getNext() {
        return next;
    }

    // Setter Methods

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
