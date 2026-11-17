package com.example.Etaalim_backend.course.controller;

import com.example.Etaalim_backend.course.dto.CourseResponse;
import com.example.Etaalim_backend.course.dto.CreateCourseRequest;
import com.example.Etaalim_backend.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(
            @Valid @RequestBody CreateCourseRequest request
    ) {
        return courseService.create(request);
    }

    @GetMapping
    public List<CourseResponse> findAll() {
        return courseService.findAll();
    }
}