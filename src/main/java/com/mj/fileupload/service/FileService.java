package com.mj.fileupload.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mj.fileupload.helper.CSVHelper;
import com.mj.fileupload.model.Movie;
import com.mj.fileupload.repository.MovieRepository;

@Service
public class FileService {
	@Autowired
	MovieRepository repository;

	public void save(MultipartFile file) {
		try {
			List<Movie> movies = CSVHelper.csvToMovies(file.getInputStream());
			repository.saveAll(movies);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<Movie> getAllMovies() {
		return repository.findAll();
	}

	public ByteArrayInputStream load() {
		List<Movie> movies = repository.findAll();
		ByteArrayInputStream in = CSVHelper.moviesToCSV(movies);
		return in;
	}
}