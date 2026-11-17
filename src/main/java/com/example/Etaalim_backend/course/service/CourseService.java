package com.example.Etaalim_backend.course.service;

import com.example.Etaalim_backend.course.dto.CourseResponse;
import com.example.Etaalim_backend.course.dto.CreateCourseRequest;
import com.example.Etaalim_backend.course.entity.Course;
import com.example.Etaalim_backend.course.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseResponse create(CreateCourseRequest request) {
        Course course = new Course();

        course.setTitle(request.getTitle().trim());
        course.setDescription(request.getDescription().trim());

        Course savedCourse = courseRepository.save(course);

        return toResponse(savedCourse);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CourseResponse toResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getStatus(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}