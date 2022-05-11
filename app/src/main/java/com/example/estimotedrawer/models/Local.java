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

    public Local() {
        listEstimotes = new ArrayList<>();
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

    public void addEstimote(Estimote e){
        listEstimotes.add(e);
    }

    public ArrayList<Estimote> getListEstimotes() {
        return listEstimotes;
    }

    public void setListEstimotes(ArrayList<Estimote> listEstimotes) {
        this.listEstimotes = listEstimotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return capacityActual == local.capacityActual && capacityMax == local.capacityMax && Double.compare(local.capacityPorcentage, capacityPorcentage) == 0 && Objects.equals(name, local.name) && Objects.equals(listEstimotes, local.listEstimotes) && Objects.equals(UrlMenu, local.UrlMenu);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Local{" +
                "name='" + name + '\'' +
                ", UrlMenu='" + UrlMenu + '\'' +
                ", capacityActual=" + capacityActual +
                ", capacityMax=" + capacityMax +
                ", capacityPorcentage=" + capacityPorcentage +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, listEstimotes, UrlMenu, capacityActual, capacityMax, capacityPorcentage);
    }

    public interface onLocalSeleccionad{
        public void onResultadoLocal(String urlWeb);
    }
    public interface onLocalNumberPhone{
        public void onResultadoNumberphone2(String numberPhone);
    }

}
