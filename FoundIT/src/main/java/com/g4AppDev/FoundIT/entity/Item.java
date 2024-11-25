package com.g4AppDev.FoundIT.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    
    
    private String description;
    private Date dateLostOrFound;
    private String location;
    private String status;
    //private boolean isClaimed
    	
    @Lob  // Marks the field for large text storage (for large base64 data)
    private String image;
    // Many-to-one relationship with UserEntity, representing the user who registered the item
    
    
  	@ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;  // Renamed from registeredByy for clarity

    @ManyToMany(mappedBy = "items")
    private List<Point> points;

    public Item() {}

    public Item(String description, Date dateLostOrFound, UserEntity user, String location, String status) {
        this.description = description;
        this.dateLostOrFound = dateLostOrFound;
       this.user = user;
        this.location = location;
        this.status = status;
    }
   
        
 public Item(String description, Date dateLostOrFound, String location, String status) {
        this.description = description;
        this.dateLostOrFound = dateLostOrFound;
      
        this.location = location;
        this.status = status;
    }
   
 
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    public Long getItemID() {
        return itemId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
              ", registeredBy=" + (user != null ? user.getUserID() : null) + // Prevent full serialization
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
