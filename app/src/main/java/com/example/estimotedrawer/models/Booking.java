package com.example.estimotedrawer.models;


import androidx.recyclerview.widget.RecyclerView;
import com.example.estimotedrawer.ui.reservas.MyAdapterBooking;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

public class Booking implements Serializable {
    private int id;
    private String localName;
    private String phone;
    private String dateBooking;
    private String madeDateBooking;
    private int people;
    private String hora;

    public Booking() {

    }

    @Override
    public String toString() {
        return "Booking{" +
                "localName='" + localName + '\'' +
                ", phone='" + phone + '\'' +
                ", dateBooking='" + dateBooking + '\'' +
                ", madeDateBooking='" + madeDateBooking + '\'' +
                ", people=" + people +
                ", hora='" + hora + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return people == booking.people && Objects.equals(localName, booking.localName) && Objects.equals(phone, booking.phone) && Objects.equals(dateBooking, booking.dateBooking) && Objects.equals(madeDateBooking, booking.madeDateBooking) && Objects.equals(hora, booking.hora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localName, phone, dateBooking, madeDateBooking, people, hora);
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getMadeDateBooking() {
        return madeDateBooking;
    }

    public void setMadeDateBooking(String madeDateBooking) {
        this.madeDateBooking = madeDateBooking;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Booking(int id, String localName, String phone, String dateBooking, String madeDateBooking, int people, String hora) {
        this.id = id;
        this.localName = localName;
        this.phone = phone;
        this.dateBooking = dateBooking;
        this.madeDateBooking = madeDateBooking;
        this.people = people;
        this.hora = hora;
    }

    public interface onDeleteBooking{
        public void onResultadoDeletBooking(int idBooking, MyAdapterBooking adapter,int position);
    }
}
