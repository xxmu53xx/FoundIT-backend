package com.g4AppDev.FoundIT.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;
    private String description;
    private Date dateLostOrFound;
    private int registeredBy;
    private String location;
    private String status;
    // Constructors
    public Item() {}

    public Item(String description, Date dateLostOrFound, int registeredBy, String location,String status) {
        this.description = description;
        this.dateLostOrFound = dateLostOrFound;
        this.registeredBy = registeredBy;
        this.location = location;
        this.status=status;
    }

    // Getters and Setters
    // Add these for all fields
    public Long  getItemID() {
        return itemId;
    }
    
    public String getStatus() {
    	return this.status;
    }
    
    public void setStatus(String status) {
    	this.status=status;
    }

    public void setItemId(Long  itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateLostOrFound() {
        return dateLostOrFound;
    }

    public void setDateLostOrFound(Date dateLostOrFound) {
        this.dateLostOrFound = dateLostOrFound;
    }

    public int getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(int registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +	
                ", description='" + description + '\'' +
                ", dateLostOrFound=" + dateLostOrFound +
                ", registeredBy=" + registeredBy +
                ", location='" + location + '\'' +
                '}';
    }

   
}