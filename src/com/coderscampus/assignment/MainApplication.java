package com.coderscampus.assignment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainApplication {

	public static void main(String[] args) {
		DataService dataService = new DataService();
		CompletableFuture<List<Integer>> fetchDataFuture = dataService.fetchData();
		List<Integer> numbers = fetchDataFuture.join(); // Wait for fetchDataFuture to complete and retrieve the numbers
		dataService.countData(numbers); // Pass the populated numbers list to countData method
	}
}
