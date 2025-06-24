package com.example.courses_api.service;

import com.example.courses_api.model.Course;
import com.example.courses_api.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        List<Course> validPrereqs = new ArrayList<>();

        for (Course prereq : course.getPrerequisites()) {
            Optional<Course> existing = courseRepository.findByCourseId(prereq.getCourseId());
            if (existing.isEmpty()) {
                throw new IllegalArgumentException("Invalid prerequisite: " + prereq.getCourseId());
            }
            validPrereqs.add(existing.get());
        }

        course.setPrerequisites(validPrereqs);
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(String courseId) {
        return courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    @Transactional
    public void deleteCourse(String courseId) {
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        boolean isDependency = courseRepository.existsByPrerequisitesContaining(course);
        if (isDependency) {
            throw new IllegalStateException("Cannot delete. Course is a prerequisite for other courses.");
        }

        courseRepository.delete(course);
    }
}
