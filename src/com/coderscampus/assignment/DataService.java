package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataService {
	
	public static void fetchData() {
		
		Assignment8 assignment8 = new Assignment8();
		
		List<Integer> numbers = new ArrayList<>();
		List<CompletableFuture<Void>> task = new ArrayList<>();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i = 0; i<1000; i++) {
			CompletableFuture<Void> tasks = CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), executor)
						                                     .thenAcceptAsync(numbers::addAll, executor);
			task.add(tasks);
		}
		
		executor.shutdown();
	}

}