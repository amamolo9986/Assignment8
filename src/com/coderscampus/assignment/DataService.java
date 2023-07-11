package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataService {
	
	public CompletableFuture<List<Integer>> fetchData() {
		Assignment8 assignment8 = new Assignment8();
		
		List<Integer> numbers = new ArrayList<>();
		List<CompletableFuture<Void>> task = new ArrayList<>();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i = 0; i<1000; i++) {
			CompletableFuture<Void> tasks = CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), executor)
						                                     .thenAcceptAsync(numbers::addAll, executor);
			task.add(tasks);
		}
		
		CompletableFuture<Void> allTasks =  CompletableFuture.allOf(task.toArray(new CompletableFuture[0]));
        return allTasks.thenApplyAsync(v -> numbers);
	}
	
	public void countData(List<Integer> numbers) {
		Map<Integer, Integer> countMap = numbers.stream()
												.collect(Collectors.toMap(Function.identity(), duplicateValue -> 1, Integer::sum));
        countMap.forEach((number, count) -> System.out.println(number + "=" + count));
	}
}