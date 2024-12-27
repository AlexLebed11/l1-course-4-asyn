package org.example;
import org.openjdk.jmh.annotations.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class StreamBenchmark {

    private static final int SIZE = 10000000;
    private List<Integer> numbers;

    @Setup
    public void setup() {
        Random random = new Random();
        numbers = IntStream.generate(() -> random.nextInt(100) + 1)
                .limit(SIZE)
                .boxed()
                .collect(Collectors.toList());
    }

    //---Parallel Stream---
    //        |
    //        V

    // Знаходження суми елементів
    @Benchmark
    public long sumWithParallelStream() {
        return numbers.parallelStream().mapToLong(Integer::longValue).sum();
    }

    // Обчислення середнього значення
    @Benchmark
    public double averageWithParallelStream() {
        return numbers.parallelStream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
    }

    // Обчислення стандартного відхилення
    @Benchmark
    public double stdDeviationWithParallelStream() {
        double mean = numbers.parallelStream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        double variance = numbers.parallelStream()
                .mapToDouble(i -> Math.pow(i - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    // Множення кожного елемента на 2
    @Benchmark
    public List<Integer> multiplyByTwoWithParallelStream() {
        return numbers.parallelStream().map(i -> i * 2).collect(Collectors.toList());
    }

    // Фільтрація (парні, що діляться на 3)
    @Benchmark
    public List<Integer> filterEvenAndDivisibleByThreeWithParallelStream() {
        return numbers.parallelStream()
                .filter(i -> i % 2 == 0 && i % 3 == 0)
                .collect(Collectors.toList());
    }



    //---Stream---
    //     |
    //     V

    // Знаходження суми елементів
    @Benchmark
    public long sumWithStream() {
        return numbers.stream().mapToLong(Integer::longValue).sum();
    }

    // Обчислення середнього значення
    @Benchmark
    public double averageWithStream() {
        return numbers.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
    }

    // Обчислення стандартного відхилення
    @Benchmark
    public double stdDeviationWithStream() {
        double mean = numbers.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        double variance = numbers.stream()
                .mapToDouble(i -> Math.pow(i - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    // Множення кожного елемента на 2
    @Benchmark
    public List<Integer> multiplyByTwoWithStream() {
        return numbers.stream().map(i -> i * 2).collect(Collectors.toList());
    }

    // Фільтрація (парні, що діляться на 3)
    @Benchmark
    public List<Integer> filterEvenAndDivisibleByThreeWithStream() {
        return numbers.stream()
                .filter(i -> i % 2 == 0 && i % 3 == 0)
                .collect(Collectors.toList());
    }
}
