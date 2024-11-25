package com.g4AppDev.FoundIT.controller;

import com.g4AppDev.FoundIT.entity.Point;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.repository.UserRepo;
import com.g4AppDev.FoundIT.service.PointService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/points")
@Validated
public class PointController {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserRepo userRepository;

    // Create Point
    @PostMapping("/postPoints")
    public ResponseEntity<?> createPoint(@RequestBody Point point) {
        try {
            // Validate positive points
            if (point.getPointsEarned() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Points earned must be positive"));
            }

            // Fetch the associated user
            UserEntity user = userRepository.findById(point.getUser().getUserID())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + point.getUser().getUserID()));

            // Add points to user's current points
            user.setCurrentPoints(user.getCurrentPoints() + point.getPointsEarned());
            userRepository.save(user);

            // Save the point
            Point createdPoint = pointService.createPoint(point);

            // Response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Point created successfully");
            response.put("createdPoint", createdPoint);
            response.put("updatedUserPoints", user.getCurrentPoints());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to create point: " + e.getMessage()));
        }
    }

    // Get All Points
    @GetMapping("/getAllPoints")
    public ResponseEntity<List<Point>> getAllPoints() {
        List<Point> points = pointService.getAllPoints();
        return ResponseEntity.ok(points);
    }

   /* // Get Point by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPointById(@PathVariable Long id) {
        return pointService.getPointById(id)
            .map(point -> ResponseEntity.ok(point))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Point not found with ID: " + id)));
    }*/

    // Update Point
    @PutMapping("/putPoint/{id}")
    public ResponseEntity<?> updatePoint(@PathVariable Long id, @RequestBody Point point) {
        try {
            point.setPointId(id); // Ensure the correct ID is set
            Point oldPoint = pointService.getPointById(id)
                .orElseThrow(() -> new EntityNotFoundException("Point not found with ID: " + id));

            // Calculate the difference in points
            int pointDifference = point.getPointsEarned() - oldPoint.getPointsEarned();

            // Update user's current points based on the difference
            UserEntity user = oldPoint.getUser();
            user.setCurrentPoints(user.getCurrentPoints() + pointDifference);
            userRepository.save(user);

            // Update the point
            Point updatedPoint = pointService.updatePoint(id, point);

            return ResponseEntity.ok(updatedPoint);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Point not found with ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to update point: " + e.getMessage()));
        }
    }

    // Delete Point and Update User's Current Points
    @DeleteMapping("/deletePoints/{id}")
    @Transactional
    public ResponseEntity<?> deletePoint(@PathVariable Long id) {
        try {
            // Fetch the point to delete
            Point point = pointService.getPointById(id)
                .orElseThrow(() -> new EntityNotFoundException("Point not found with ID: " + id));

            // Get user and deduct points
            UserEntity user = point.getUser();
            int pointsToDeduct = point.getPointsEarned();

            // Remove the point from the user's total points
            user.setCurrentPoints(user.getCurrentPoints() - pointsToDeduct);

            // Recalculate the current points based on all remaining points
            List<Point> userPoints = pointService.getPointsByUser(user);
            int totalPoints = userPoints.stream().mapToInt(Point::getPointsEarned).sum();
            user.setCurrentPoints(totalPoints);

            userRepository.save(user);

            // Delete the point
            pointService.deletePoint(id);

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Point deleted successfully");
            response.put("pointsDeducted", pointsToDeduct);
            response.put("currentPoints", user.getCurrentPoints());

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to delete point: " + e.getMessage()));
        }
    }
    // Get Latest Points
    @GetMapping("/getLatestPoints")
    public ResponseEntity<List<Point>> getLatestPoints(@RequestParam(defaultValue = "5") int count) {
        List<Point> points = pointService.getLatestPoints(count);
        return ResponseEntity.ok(points);
    }

    // Get Points Stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getPointStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPoints", pointService.getPointCount());
        stats.put("latestPoints", pointService.getLatestPoints(5)); // Example for recent points
        return ResponseEntity.ok(stats);
    }

    // Entity Counts (Existing Endpoint)
    @GetMapping("/getCountPoints")
    public ResponseEntity<Map<String, Long>> getEntityCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("point_count", pointService.getPointCount());
        return ResponseEntity.ok(counts);
    }
}
