package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] gridState;

    private int numOfOpen;
    private int virtualTop;  // for the sake of O(1)
    private int virtualBottom;
    private WeightedQuickUnionUF UF;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N)   {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0.");
        }

        gridState = new boolean[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                gridState[i][j] = false;  // 0 stands for blocked
            }
        }
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        numOfOpen = 0;
        UF = new WeightedQuickUnionUF(N * N + 2);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int N = gridState.length;
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("out of bound.");
        }

        if (!gridState[row][col]) {
            gridState[row][col] = true;  // 1 stands for open
            ++numOfOpen;
        }

        int index = row * N + col;
        if (row == 0) {
            UF.union(virtualTop, index);
        }
        if (row == N - 1) {
            UF.union(virtualBottom, index);
        }
        if (index - 1 >= 0 && col - 1 >= 0 && isOpen(row, col - 1)) {
            if (isFull(row, col - 1)) {
                UF.union(virtualTop, index);
                UF.union(virtualTop, index - 1);
            } else {
                UF.union(index, index - 1);
            }
        }
        if (index + 1 < N * N && col + 1 < N && isOpen(row, col + 1)) {
            if (isFull(row, col + 1)) {
                UF.union(virtualTop, index);
                UF.union(virtualTop, index + 1);
            } else {
                UF.union(index, index + 1);
            }
        }
        if (index - N >= 0 && row - 1 >= 0 && isOpen(row - 1, col)) {
            if (isFull(row - 1, col)) {
                UF.union(virtualTop, index);
                UF.union(virtualTop, index - N);
            } else {
                UF.union(index, index - N);
            }
        }
        if (index + N < N * N && row + 1 < N && isOpen(row + 1, col)) {
            if (isFull(row + 1, col)) {
                UF.union(virtualTop, index);
                UF.union(virtualTop, index + N);
            } else {
                UF.union(index, index + N);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int N = gridState.length;
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("out of bound.");
        }
        return gridState[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int N = gridState.length;
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("out of bound.");
        }
        if (!isOpen(row, col)) {
            return false;
        }

        int index = row * N + col;
        if (row == 0) {
            return true;
        }
        // avoid backwash
        if (col - 1 >= 0 && index - 1 >= 0 && UF.connected(index - 1, virtualTop)) {
            return true;
        }
        if (col + 1 < N && index + 1 < N * N && UF.connected(index + 1, virtualTop)) {
            return true;
        }
        if (row - 1 >= 0 && index - N >= 0 && UF.connected(index - N, virtualTop)) {
            return true;
        }
        if (row + 1 < N && index + N < N * N && UF.connected(index + N, virtualTop)) {
            return true;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.connected(virtualTop, virtualBottom);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
