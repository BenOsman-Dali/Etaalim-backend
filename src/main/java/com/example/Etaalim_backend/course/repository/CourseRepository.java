package com.example.Etaalim_backend.course.repository;

import com.example.Etaalim_backend.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
}