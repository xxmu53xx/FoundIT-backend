package com.g4AppDev.FoundIT.service;

import com.g4AppDev.FoundIT.entity.Point;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    // Create
    public Point createPoint(int pointsEarned, Date dateEarned) {
    	 Point point = new Point(pointsEarned, dateEarned);
        return pointRepository.save(point);
    }

    // Read all
    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    // Read one
    public Optional<Point> getPointById(Long id) {
        return pointRepository.findById(id);
    }

    // Update
    public Point updatePoint(Point point) {
        return pointRepository.save(point);
    }

    // Delete
    public void deletePoint(Long id) {
        pointRepository.deleteById(id);
    }

    // Get latest points
    public List<Point> getLatestPoints(int count) {
        return pointRepository.findAll().stream()
            .sorted((p1, p2) -> p2.getDateEarned().compareTo(p1.getDateEarned())) // Sort by dateEarned descending
            .limit(count)
            .collect(Collectors.toList());
    }
}
