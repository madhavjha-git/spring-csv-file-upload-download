package com.mj.fileupload.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.mj.fileupload.model.Movie;

public class CSVHelper {
	private static Logger logger = LoggerFactory.getLogger(CSVHelper.class);
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Id", "Title", "Description", "ReleasedYear" };

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<Movie> csvToMovies(InputStream inputStream) throws UnsupportedEncodingException {

		final CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true)
				.setIgnoreHeaderCase(true).setTrim(true).build();

		BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		logger.info("Reading stream");

		List<Movie> movies = new ArrayList<Movie>();
		try {
			Iterable<CSVRecord> records = csvFormat.parse(fileReader);
			for (CSVRecord csvRecord : records) {
				Movie movie = new Movie(Long.parseLong(csvRecord.get("Id")), csvRecord.get("Title"),
						csvRecord.get("Description"), Integer.parseInt(csvRecord.get("ReleasedYear")));
				movies.add(movie);
			}
			return movies;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public static ByteArrayInputStream moviesToCSV(List<Movie> movies) {
		final CSVFormat format = CSVFormat.Builder.create().setHeader(HEADERs).setQuoteMode(QuoteMode.MINIMAL).build();

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (Movie movie : movies) {
				List<String> data = Arrays.asList(String.valueOf(movie.getId()), movie.getTitle(),
						movie.getDescription(), String.valueOf(movie.getRelasedYear()));
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
}