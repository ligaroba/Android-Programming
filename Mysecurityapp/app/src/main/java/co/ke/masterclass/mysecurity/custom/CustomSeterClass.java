package co.ke.masterclass.mysecurity.custom;

import android.net.Uri;

import java.util.HashMap;

/**
 * Created by root on 9/30/14.
 */
public class CustomSeterClass {

    private  String name;
    private  String dateReported;
    private  String gender;
    private  String description;
    private  String photo;
    private  String location;
    private int id;
    private String contact;
    private String organme;
    private Uri path;


    public Uri getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path = path;
    }

    public String getOrganme() {
        return organme;
    }

    public void setOrganme(String organme) {
        this.organme = organme;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getId() { return id;
    }

    public void setId(int id) { this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateReported() {
        return dateReported;
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getLocation() {
        return location;
    }


}
