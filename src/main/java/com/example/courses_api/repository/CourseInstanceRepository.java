package com.example.courses_api.repository;

import com.example.courses_api.model.CourseInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseInstanceRepository extends JpaRepository<CourseInstance, Long> {
    List<CourseInstance> findByYearAndSemester(int year, int semester);
    Optional<CourseInstance> findByYearAndSemesterAndCourse_CourseId(int year, int semester, String courseId);
    void deleteByYearAndSemesterAndCourse_CourseId(int year, int semester, String courseId);
}
