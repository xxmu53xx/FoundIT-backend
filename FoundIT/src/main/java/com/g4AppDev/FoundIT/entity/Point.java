package com.g4AppDev.FoundIT.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private int pointsEarned;
    private Date dateEarned;

  
    public Point() {}

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

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public Date getDateEarned() {
        return dateEarned;
    }

    public void setDateEarned(Date dateEarned) {
        this.dateEarned = dateEarned;
    }

    @Override
    public String toString() {
        return "Point{" +
                "pointId=" + pointId +
                ", pointsEarned=" + pointsEarned +
                ", dateEarned=" + dateEarned +
               
                '}';
    }
    /*
    @Override
    public String toString() {
        return "Point{" +
                "pointId=" + pointId +
                ", pointsEarned=" + pointsEarned +
                ", dateEarned=" + dateEarned +
                ", user=" + (user != null ? user.getUserID() : null) +
                '}';
    }*/
}
