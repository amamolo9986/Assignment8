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
		                .thenAcceptAsync(data -> {
		                    synchronized (numbers) {
		                        numbers.addAll(data);
		                    }
		                }, executor);
		        task.add(tasks);
		    }
		
		CompletableFuture<Void> allTasks = CompletableFuture.allOf(task.toArray(new CompletableFuture[0])); 
		
        return allTasks.thenApplyAsync(allTasksComplete -> numbers); 
	}
	
	public void countData(List<Integer> numbers) {
		Map<Integer, Integer> countMap = numbers.stream()
												.collect(Collectors.toMap(Function.identity(), initialValue -> 1, Integer::sum));
        countMap.forEach((number, count) -> System.out.println(number + "=" + count));
	}
}

//    OLD WAY
//public CompletableFuture<List<Integer>> fetchData() { 
//	Assignment8 assignment8 = new Assignment8();
//	
//	List<CompletableFuture<List<Integer>>> tasks = new ArrayList<>();
//	
//	ExecutorService executor = Executors.newCachedThreadPool();
//	
//	for(int i = 0; i<1000; i++) {
//		CompletableFuture<List<Integer>> task  = CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), executor);
//					                                     
//		tasks.add(task);
//	}
//	
//	return CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]))
//           .thenApply(v -> tasks.stream()
//            					.map(CompletableFuture::join)
//            					.flatMap(List::stream)
//            					.collect(Collectors.toList()));