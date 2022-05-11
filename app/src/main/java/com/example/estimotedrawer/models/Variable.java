package com.example.estimotedrawer.models;

public class Variable {

    private  int id;
    private String name;
    private  int value;

    public Variable(int id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Variable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
