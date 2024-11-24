package com.g4AppDev.FoundIT.service;

import com.g4AppDev.FoundIT.entity.Point;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.PointRepository;
import com.g4AppDev.FoundIT.repository.UserRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;
   /* public Point createPoint(int pointsEarned, Date dateEarned) {
    	 Point point = new Point(pointsEarned, dateEarned);
        return pointRepository.save(point);
    }*/
    @Autowired
    private EntityManager entityManager;
    public boolean existsById(Long id) {
        return pointRepository.existsById(id);
    }
    public Point createPoint(Point point) {
   
    	return pointRepository.save(point);
    }

    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    public Optional<Point> getPointById(Long id) {
        return pointRepository.findById(id);
    }

    public Point updatePoint(Long id,Point point) {
    	Point point1;
    	try {
    		point1 = pointRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Location not found"));
 
    		point1.setDateEarned(point.getDateEarned());
    		point1.setPointsEarned(point.getPointsEarned());
    		
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Point with ID " + id + " not found!");
        }
        return pointRepository.save(point);
        
    }
/*
    public void deletePoint(Long id) {

    }*/
    public void deletePoint(Long id) {
        Point point = pointRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Point not found with id: " + id));
        
        UserEntity user = point.getUser();
        if (user != null) {
            // Update user's current points if needed
            user.setCurrentPoints(user.getCurrentPoints() - point.getPointsEarned());
            
            // Remove the point from user's points list
            user.getPoints().remove(point);
            
            // Merge the updated user
            entityManager.merge(user);
        }
        
        // Delete the point
        pointRepository.delete(point);
        entityManager.flush();
    }


    public List<Point> getLatestPoints(int count) {
        return pointRepository.findAll().stream()
            .sorted((p1, p2) -> p2.getDateEarned().compareTo(p1.getDateEarned()))
            .limit(count)
            .collect(Collectors.toList());
    }
    
    public long getPointCount() {
        return pointRepository.count();
    }
}
