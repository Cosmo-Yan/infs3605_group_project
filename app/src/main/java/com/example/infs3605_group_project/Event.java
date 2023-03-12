package com.example.infs3605_group_project;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.Date;

/* This class represents a table in the database and defines its schema.
   It is annotated with @Entity and contains a primary key and one or more columns
*/
    @Entity
    public class Event {
    @PrimaryKey
    @NonNull
        private int id;
        private String eventName;
        private String nameOfOrganiser;
        private String eventType;
        private String country;
        private String location;

        @ColumnInfo(name = "eventStartDate")
        private Date eventStartDate;
        private String furtherDetails; //Can be null
        private byte[] image;   // Byte array to store image data

    //Constructors, Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getNameOfOrganiser() {
        return nameOfOrganiser;
    }

    public void setNameOfOrganiser(String nameOfOrganiser) {
        this.nameOfOrganiser = nameOfOrganiser;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getFurtherDetails() {
        return furtherDetails;
    }

    public void setFurtherDetails(String furtherDetails) {
        this.furtherDetails = furtherDetails;
    }

    @TypeConverters({typeConverter.class})
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}





