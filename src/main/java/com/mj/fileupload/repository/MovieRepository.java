package com.mj.fileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mj.fileupload.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}