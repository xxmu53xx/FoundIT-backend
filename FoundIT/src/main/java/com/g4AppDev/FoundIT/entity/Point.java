package com.g4AppDev.FoundIT.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private int pointsEarned;
    private Date dateEarned;
    
    @ManyToOne(fetch = FetchType.LAZY)
    
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;

    @ManyToMany
    @JoinTable(
        name = "point_item",
        joinColumns = @JoinColumn(name = "point_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;
  
    public Point() {}

    // Updated constructor to include UserEntity
    public Point(int pointsEarned, Date dateEarned, UserEntity user) {
        this.pointsEarned = pointsEarned;
        this.dateEarned = dateEarned;
        this.user = user; // Set the user reference
    }
    
    public Point(int pointsEarned, Date dateEarned) {
        this.pointsEarned = pointsEarned;
        this.dateEarned = dateEarned;
    
    }

    // Getters and Setters
    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }
    @PreRemove
    private void removePointFromUser() {
        if (user != null) {
            user.getPoints().remove(this);
        }}
    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public Date getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Point{" +
                "pointId=" + pointId +
                ", pointsEarned=" + pointsEarned +
                ", dateEarned=" + dateEarned +
                ", user=" + (user != null ? user.getUserID() : null) +
                '}';
    }
}
