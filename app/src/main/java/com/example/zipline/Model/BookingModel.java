package com.example.zipline.Model;

public class BookingModel {
    private int booking_id, user_id, total_people, total_amount;
    private String zipline_type, tourist,date;

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTotal_people() {
        return total_people;
    }

    public void setTotal_people(int total_people) {
        this.total_people = total_people;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getZipline_type() {
        return zipline_type;
    }

    public void setZipline_type(String zipline_type) {
        this.zipline_type = zipline_type;
    }

    public String getTourist() {
        return tourist;
    }

    public void setTourist(String tourist) {
        this.tourist = tourist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
