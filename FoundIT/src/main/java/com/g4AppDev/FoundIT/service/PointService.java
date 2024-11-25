package com.g4AppDev.FoundIT.service;

import com.g4AppDev.FoundIT.entity.Point;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.PointRepository;
import com.g4AppDev.FoundIT.repository.UserRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EntityManager entityManager;

    public boolean existsById(Long id) {
        return pointRepository.existsById(id);
    }

    // Add points and update user's current points
    public Point addPoint(Point point, Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        point.setUser(user);
        user.setCurrentPoints(user.getCurrentPoints() + point.getPointsEarned());
        
        userRepository.save(user); // Save user with updated points
        return pointRepository.save(point); // Save point with user reference
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

    
    /*
    @Transactional
    public Point updatePoint(Long id, Point updatedPoint) {
        Point point = pointRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Point with ID " + id + " not found!"));

        point.setDateEarned(updatedPoint.getDateEarned());
        point.setPointsEarned(updatedPoint.getPointsEarned());
        
        return pointRepository.save(point);
    }*/
    @Transactional
    public Point updatePoint(Long id, Point updatedPoint) {
        Point point = pointRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Point with ID " + id + " not found!"));

        // Deduct the old points from the user's current points
        UserEntity user = point.getUser();
        if (user != null) {
            user.setCurrentPoints(user.getCurrentPoints() - point.getPointsEarned());
        }

        // Update the point's details
        point.setDateEarned(updatedPoint.getDateEarned());
        point.setPointsEarned(updatedPoint.getPointsEarned());

        // Add the new points to the user's current points
        if (user != null) {
            user.setCurrentPoints(user.getCurrentPoints() + point.getPointsEarned());
            userRepository.save(user);
        }

        return pointRepository.save(point);
    }
/*
    @Transactional
    public void deletePoint(Long pointId) {
        Point point = pointRepository.findById(pointId)
            .orElseThrow(() -> new EntityNotFoundException("Point not found with id: " + pointId));

        UserEntity user = point.getUser();
        if (user != null) {
            // Deduct points from user's current points
            int updatedPoints = user.getCurrentPoints() - point.getPointsEarned();
            user.setCurrentPoints(Math.max(0, updatedPoints));
        }
        
        pointRepository.delete(point); // Deletes point and triggers PreRemove logic
    }*/
    @Transactional
    public void deletePoint(Long pointId) {
        Point point = pointRepository.findById(pointId)
            .orElseThrow(() -> new EntityNotFoundException("Point not found with id: " + pointId));

        UserEntity user = point.getUser();
        if (user != null) {
            // Deduct the point from user's current points
            int updatedPoints = user.getCurrentPoints() - point.getPointsEarned();
            user.setCurrentPoints(Math.max(0, updatedPoints));  // Ensure it doesn't go negative
            userRepository.save(user);
        }

        // Delete the point
        pointRepository.delete(point);

        // Recalculate and verify the user's total points after deletion
        verifyUserPoints(user.getUserID());
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

    @Transactional
    public void verifyUserPoints(Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        int calculatedTotal = user.getPoints().stream()
            .mapToInt(Point::getPointsEarned)
            .sum();

        if (user.getCurrentPoints() != calculatedTotal) {
            user.setCurrentPoints(calculatedTotal);
            userRepository.save(user);
        }
    }
    

    public List<Point> getPointsByUser(UserEntity user) {
        return pointRepository.findByUser(user);
    }
}
