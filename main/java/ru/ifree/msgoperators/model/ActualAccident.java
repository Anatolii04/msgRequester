package ru.ifree.msgoperators.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.Timestamp;


@JsonIgnoreProperties("new")

public class ActualAccident {
    private Integer id;
    private String node;
    private Timestamp timestamp;
    private Boolean actual;

    public ActualAccident(Integer id, String node, Timestamp timestamp, Boolean actual) {
        this.id = id;
        this.node = node;
        this.timestamp = timestamp;
        this.actual = actual;
    }

    public ActualAccident() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    public boolean isNew() {
        return (getId() == null);
    }

}
