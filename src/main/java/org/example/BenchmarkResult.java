package org.example;
/**
 * Класс для хранения результатов бенчмарка
 */
public class BenchmarkResult {
    private final String operationType;
    private final double avgTimeNs;
    private final double avgOperations;

    public BenchmarkResult(String operationType, double avgTimeNs, double avgOperations) {
        this.operationType = operationType;
        this.avgTimeNs = avgTimeNs;
        this.avgOperations = avgOperations;
    }

    @Override
    public String toString() {
        return String.format("%s: avg time=%.2f ns, avg ops=%.2f",
                operationType, avgTimeNs, avgOperations);
    }
}