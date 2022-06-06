package com.example.estimotedrawer.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import lombok.Data;

@Data
public class Local implements Serializable {
    private String name;
    private ArrayList<Estimote> listEstimotes;
    private String UrlMenu;
    private String UUID;
    private int capacityActual;
    private int capacityMax;
    private double capacityPorcentage;
    private String phone;
    private String photoURL;
    private ArrayList<Review> listReviews;
    private String urlToGoogle;

    public Local() {
        listEstimotes = new ArrayList<>();
        listReviews = new ArrayList<>();
    }

    public String getUrlToGoogle() {
        return urlToGoogle;
    }

    public void setUrlToGoogle(String urlToGoogle) {
        this.urlToGoogle = urlToGoogle;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrlMenu() {
        return UrlMenu;
    }

    public void setUrlMenu(String urlMenu) {
        UrlMenu = urlMenu;
    }

    public ArrayList<Review> getListReviews() {
        return listReviews;
    }

    public void setListReviews(ArrayList<Review> listReviews) {
        this.listReviews = listReviews;
    }

    public int getCapacityActual() {
        return capacityActual;
    }

    public void setCapacityActual(int capacityActual) {
        this.capacityActual = capacityActual;
    }

    public int getCapacityMax() {
        return capacityMax;
    }

    public void setCapacityMax(int capacityMax) {
        this.capacityMax = capacityMax;
    }

    public double getCapacityPorcentage() {
        return capacityPorcentage;
    }

    public void setCapacityPorcentage(double capacityPorcentage) {
        this.capacityPorcentage = capacityPorcentage;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }



    public ArrayList<Estimote> getListEstimotes() {
        return listEstimotes;
    }

    public void setListEstimotes(ArrayList<Estimote> listEstimotes) {
        this.listEstimotes = listEstimotes;
    }


    public void addEstimote(Estimote e){
        listEstimotes.add(e);
    }
    public void addReview(Review e){
        listReviews.add(e);
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public interface onLocalSeleccionad{
        public void onResultadoLocal(String urlWeb);
    }
    public interface onLocalNumberPhone{
        public void onResultadoNumberphone2(String numberPhone);
    }
    public interface onLocalReview{
        public void onResultadoLocalReview(String localName);
    }

    @Override
    public String toString() {
        return "Local{" +
                "name='" + name + '\'' +
                '}';
    }
}
