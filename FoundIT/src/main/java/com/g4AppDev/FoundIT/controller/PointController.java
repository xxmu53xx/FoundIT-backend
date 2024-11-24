package com.g4AppDev.FoundIT.controller;

import com.g4AppDev.FoundIT.entity.Point;
import com.g4AppDev.FoundIT.service.PointService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/points")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/postPoints" )
    public Point createPoint(@RequestBody Point point) {
      return pointService.createPoint(point);
    }

    @GetMapping("/getAllPoints")
    public ResponseEntity<List<Point>> getAllPoints() {
        List<Point> points = pointService.getAllPoints();
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Point> getPointById(@PathVariable Long id) {
        Optional<Point> point = pointService.getPointById(id);
        return point.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/putPoint/{id}")
    public ResponseEntity<Point> updatePoint(@PathVariable Long id, @RequestBody Point point) {
        try {
            // Set the ID from the path parameter to ensure we're updating the correct record
            point.setPointId(id);
            Point updatedPoint = pointService.updatePoint(id, point);
            
            if (updatedPoint != null) {
                return ResponseEntity.ok(updatedPoint);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   
    @DeleteMapping("/deletePoints/{id}")
    @Transactional
    public ResponseEntity<?> deletePoint(@PathVariable Long id) {
        try {
            Point point = pointService.getPointById(id)
                .orElseThrow(() -> new EntityNotFoundException("Point not found"));
                
            pointService.deletePoint(id);
            
            return ResponseEntity.ok()
                .body(Map.of("message", "Point deleted successfully", "pointId", id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to delete point: " + e.getMessage()));
        }
    }

    @GetMapping("/getLatestPoints")
    public List<Point> getLatestPoints(@RequestParam(defaultValue = "5") int count) {
        return pointService.getLatestPoints(count);
    }
    

    @GetMapping("/getCountPoints")
    public ResponseEntity<Map<String, Long>> getEntityCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("user_count", pointService.getPointCount());
        return ResponseEntity.ok(counts);
    }
}
