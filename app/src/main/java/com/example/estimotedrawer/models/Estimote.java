package com.example.estimotedrawer.models;

import java.io.Serializable;
import java.util.Objects;

public class Estimote implements Serializable {
    private int major;
    private int minor;

    public Estimote() {
    }

    public Estimote(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    @Override
    public String toString() {
        return "Estimote{" +
                "major=" + major +
                ", minor=" + minor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estimote estimote = (Estimote) o;
        return major == estimote.major && minor == estimote.minor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor);
    }
}
