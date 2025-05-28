package org.example;

/**
 * Класс, реализующий дерево отрезков для суммы на интервале.
 */
public class SegmentTree {
    private final int[] tree; // Массив для хранения дерева
    private final int[] originalData; // Оригинальные данные
    private final int size; // Размер исходных данных
    private int operationsCount; // Счетчик операций (для замера)

    public SegmentTree(int[] data) {
        this.originalData = data.clone();
        this.size = data.length;
        this.tree = new int[4 * size]; // Стандартный размер дерева
        this.operationsCount = 0;
        build(0, 0, size - 1);
    }

    private void build(int node, int start, int end) {
        operationsCount++;
        if (start == end) {
            tree[node] = originalData[start];
            return;
        }
        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        build(leftChild, start, mid);
        build(rightChild, mid + 1, end);

        tree[node] = tree[leftChild] + tree[rightChild];
    }

    public void updateValue(int index, int value) {
        operationsCount = 0;
        update(0, 0, size - 1, index, value);
    }

    private void update(int node, int start, int end, int index, int value) {
        operationsCount++;
        if (start == end) {
            originalData[index] = value;
            tree[node] = value;
            return;
        }
        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        if (index <= mid) {
            update(leftChild, start, mid, index, value);
        } else {
            update(rightChild, mid + 1, end, index, value);
        }

        tree[node] = tree[leftChild] + tree[rightChild];
    }

    public int queryRange(int l, int r) {
        operationsCount = 0;
        return query(0, 0, size - 1, l, r);
    }

    private int query(int node, int start, int end, int l, int r) {
        operationsCount++;
        if (r < start || l > end) return 0;
        if (l <= start && end <= r) return tree[node];

        int mid = (start + end) / 2;
        int leftChild = 2 * node + 1;
        int rightChild = 2 * node + 2;

        int leftSum = query(leftChild, start, mid, l, r);
        int rightSum = query(rightChild, mid + 1, end, l, r);

        return leftSum + rightSum;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public int getSize() {
        return size;
    }
}