package com.example.infs3605_group_project.Activity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Activity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("id")
    @Expose
    private Integer id;     // IDK why its integer but I recall there being an error with int

    @SerializedName("eventName")
    @Expose
    private String eventName;

    @SerializedName("eventName")
    @Expose
    private String zid;

    @SerializedName("nameOfOrganiser")
    @Expose
    private String nameOfOrganiser;

    @SerializedName("eventType")
    @Expose
    private String eventType;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("eventStartDate")
    @Expose
    private String eventStartDate;

    @SerializedName("furtherDetails")
    @Expose
    private String furtherDetails;  //Can be null

    @SerializedName("image")
    @Expose
    private String image;   // String location for the image nullable with default value

    public Activity(@NonNull Integer id, String eventName, String zid, String nameOfOrganiser, String eventType, String country, String location, String eventStartDate, String furtherDetails, String image) {
        this.id = id;
        this.zid = zid;
        this.eventName = eventName;
        this.nameOfOrganiser = nameOfOrganiser;
        this.eventType = eventType;
        this.country = country;
        this.location = location;
        this.eventStartDate = eventStartDate;
        this.furtherDetails = furtherDetails;
        this.image = image;
    }

    public Activity(String eventName, String zid, String nameOfOrganiser, String eventType, String country, String location, String eventStartDate, String furtherDetails, String image) {
        this.id = id;
        this.zid = zid;
        this.eventName = eventName;
        this.nameOfOrganiser = nameOfOrganiser;
        this.eventType = eventType;
        this.country = country;
        this.location = location;
        this.eventStartDate = eventStartDate;
        this.furtherDetails = furtherDetails;
        this.image = image;
    }


    public Activity(@NonNull Integer id, String zid, String eventName, String nameOfOrganiser, String eventType, String country, String location, String eventStartDate, String furtherDetails) {
        this.id = id;
        this.eventName = eventName;
        this.zid = zid;
        this.nameOfOrganiser = nameOfOrganiser;
        this.eventType = eventType;
        this.country = country;
        this.location = location;
        this.eventStartDate = eventStartDate;
        this.furtherDetails = furtherDetails;
    }

    public Activity(){} // Needed or database (room) won't work

    @NonNull
    public Integer getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getNameOfOrganiser() {
        return nameOfOrganiser;
    }

    public String getEventType() {
        return eventType;
    }

    public String getCountry() {
        return country;
    }

    public String getLocation() {
        return location;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public String getFurtherDetails() {
        return furtherDetails;
    }

    public String getImage() {
        return image;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setNameOfOrganiser(String nameOfOrganiser) {
        this.nameOfOrganiser = nameOfOrganiser;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public void setFurtherDetails(String furtherDetails) {
        this.furtherDetails = furtherDetails;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }
}
