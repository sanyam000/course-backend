package com.example.courses_api.controller;

import com.example.courses_api.model.CourseInstance;
import com.example.courses_api.service.CourseInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instances")
public class CourseInstanceController {

    @Autowired
    private CourseInstanceService instanceService;

    @PostMapping
    public ResponseEntity<?> createInstance(
            @RequestParam String courseId,
            @RequestParam int year,
            @RequestParam int semester) {
        try {
            CourseInstance instance = instanceService.createInstance(courseId, year, semester);
            return ResponseEntity.ok(instance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{year}/{semester}")
    public List<CourseInstance> getInstances(
            @PathVariable int year,
            @PathVariable int semester) {
        return instanceService.getInstances(year, semester);
    }

    @GetMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> getInstanceDetails(
            @PathVariable int year,
            @PathVariable int semester,
            @PathVariable String courseId) {
        try {
            return ResponseEntity.ok(instanceService.getInstanceDetails(year, semester, courseId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{year}/{semester}/{courseId}")
    public ResponseEntity<?> deleteInstance(
            @PathVariable int year,
            @PathVariable int semester,
            @PathVariable String courseId) {
        try {
            instanceService.deleteInstance(year, semester, courseId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
