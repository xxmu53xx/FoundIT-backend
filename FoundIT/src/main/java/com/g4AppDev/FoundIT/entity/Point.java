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

    public Point(int pointsEarned, Date dateEarned, UserEntity user) {
        this.pointsEarned = pointsEarned;
        this.dateEarned = dateEarned;
        this.user = user;
    }

    public Point(int pointsEarned, Date dateEarned) {
        this.pointsEarned = pointsEarned;
        this.dateEarned = dateEarned;
    }

  
    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

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
    
    private int CounterPoints;
   
    @PreRemove
    private void handleRemoval() {
        if (user != null) {
            user.getPoints().remove(this);
            CounterPoints=this.pointsEarned*2;
          
            int updatedPoints = user.getCurrentPoints() - (this.pointsEarned-CounterPoints);
            if(updatedPoints<=0) {
            	updatedPoints=0;
            }
            user.setCurrentPoints(Math.max(0, updatedPoints));
        }
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
