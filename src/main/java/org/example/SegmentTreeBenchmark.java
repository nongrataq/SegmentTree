package org.example;

import java.util.Arrays;
import java.util.Random;

/**
 * Класс для тестирования производительности SegmentTree
 */
public class SegmentTreeBenchmark {
    private static final int DATA_SIZE = 10000;
    private static final int SEARCH_TESTS = 100;
    private static final int DELETE_TESTS = 1000;

    private final Random random = new Random();
    private final int[] testData;
    private final SegmentTree segmentTree;

    public SegmentTreeBenchmark() {
        this.testData = generateRandomArray(DATA_SIZE);
        this.segmentTree = new SegmentTree(testData);
    }

    private int[] generateRandomArray(int size) {
        return new Random().ints(size, 0, 1000).toArray();
    }

    public BenchmarkResult runInsertBenchmark() {
        long[] times = new long[DATA_SIZE];
        int[] operations = new int[DATA_SIZE];

        for (int i = 0; i < DATA_SIZE; i++) {
            long start = System.nanoTime();
            segmentTree.updateValue(i, testData[i]);
            times[i] = System.nanoTime() - start;
            operations[i] = segmentTree.getOperationsCount();
        }

        double avgTime = Arrays.stream(times).average().orElse(0);
        double avgOps = Arrays.stream(operations).average().orElse(0);
        return new BenchmarkResult("Insert", avgTime, avgOps);
    }

    public BenchmarkResult runSearchBenchmark() {
        long[] times = new long[SEARCH_TESTS];
        int[] operations = new int[SEARCH_TESTS];

        for (int i = 0; i < SEARCH_TESTS; i++) {
            int index = random.nextInt(DATA_SIZE);
            long start = System.nanoTime();
            segmentTree.queryRange(index, index);
            times[i] = System.nanoTime() - start;
            operations[i] = segmentTree.getOperationsCount();
        }

        double avgTime = Arrays.stream(times).average().orElse(0);
        double avgOps = Arrays.stream(operations).average().orElse(0);
        return new BenchmarkResult("Search", avgTime, avgOps);
    }

    public BenchmarkResult runDeleteBenchmark() {
        long[] times = new long[DELETE_TESTS];
        int[] operations = new int[DELETE_TESTS];

        for (int i = 0; i < DELETE_TESTS; i++) {
            int index = random.nextInt(DATA_SIZE);
            long start = System.nanoTime();
            segmentTree.updateValue(index, 0); // Удаление = установка в 0
            times[i] = System.nanoTime() - start;
            operations[i] = segmentTree.getOperationsCount();
        }

        double avgTime = Arrays.stream(times).average().orElse(0);
        double avgOps = Arrays.stream(operations).average().orElse(0);
        return new BenchmarkResult("Delete", avgTime, avgOps);
    }

    public void runFullBenchmark() {
        System.out.println("Running benchmark with data size: " + DATA_SIZE);

        BenchmarkResult insert = runInsertBenchmark();
        BenchmarkResult search = runSearchBenchmark();
        BenchmarkResult delete = runDeleteBenchmark();

        System.out.println(insert);
        System.out.println(search);
        System.out.println(delete);

        System.out.println("\nComplexity analysis:");
        System.out.println("Expected: O(log n) for all operations");
        System.out.printf("Actual log2(%d) = %.2f\n",
                DATA_SIZE, Math.log(DATA_SIZE) / Math.log(2));
    }

    public static void main(String[] args) {
        new SegmentTreeBenchmark().runFullBenchmark();
    }
}
