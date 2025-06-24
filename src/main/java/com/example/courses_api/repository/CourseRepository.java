package com.example.courses_api.repository;

import com.example.courses_api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseId(String courseId);
    boolean existsByPrerequisitesContaining(Course course);
}
