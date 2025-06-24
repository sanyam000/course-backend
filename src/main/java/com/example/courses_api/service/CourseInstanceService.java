package com.example.courses_api.service;

import com.example.courses_api.model.Course;
import com.example.courses_api.model.CourseInstance;
import com.example.courses_api.repository.CourseInstanceRepository;
import com.example.courses_api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseInstanceService {

    @Autowired
    private CourseInstanceRepository instanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    public CourseInstance createInstance(String courseId, int year, int semester) {
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        CourseInstance instance = new CourseInstance();
        instance.setCourse(course);
        instance.setYear(year);
        instance.setSemester(semester);

        return instanceRepository.save(instance);
    }

    public List<CourseInstance> getInstances(int year, int semester) {
        return instanceRepository.findByYearAndSemester(year, semester);
    }

    public CourseInstance getInstanceDetails(int year, int semester, String courseId) {
        return instanceRepository.findByYearAndSemesterAndCourse_CourseId(year, semester, courseId)
                .orElseThrow(() -> new NoSuchElementException("Instance not found"));
    }

    public void deleteInstance(int year, int semester, String courseId) {
        instanceRepository.deleteByYearAndSemesterAndCourse_CourseId(year, semester, courseId);
    }
}
