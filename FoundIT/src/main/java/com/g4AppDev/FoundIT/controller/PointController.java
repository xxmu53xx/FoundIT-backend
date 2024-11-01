package com.g4AppDev.FoundIT.controller;

import com.g4AppDev.FoundIT.entity.Point;
import com.g4AppDev.FoundIT.entity.UserEntity;
import com.g4AppDev.FoundIT.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/points")
public class PointController {

    @Autowired
    private PointService pointService;

    // CREATE
    @PostMapping("/postPoints")
    public ResponseEntity<Point> createPoint(@RequestBody Point point) {
        Point savedPoint = pointService.createPoint(point.getPointsEarned(), point.getDateEarned());
        return new ResponseEntity<>(savedPoint, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping("/getAllPoints")
    public ResponseEntity<List<Point>> getAllPoints() {
        List<Point> points = pointService.getAllPoints();
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Point> getPointById(@PathVariable Long id) {
        Optional<Point> point = pointService.getPointById(id);
        return point.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE
    @PutMapping("/putPoint/{id}")
    public ResponseEntity<Point> updatePoint(@PathVariable Long id, @RequestBody Point point) {
        if (!pointService.getPointById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        point.setPointId(id);
        Point updatedPoint = pointService.updatePoint(point);
        return new ResponseEntity<>(updatedPoint, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/deletePoints/{id}")
    public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
        if (!pointService.getPointById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pointService.deletePoint(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET LATEST POINTS
    @GetMapping("/getLatestPoints")
    public List<Point> getLatestPoints(@RequestParam(defaultValue = "5") int count) {
        return pointService.getLatestPoints(count);
    }
}
