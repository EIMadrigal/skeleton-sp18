package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int N;
    private int T;
    private Percolation per;

    private double[] threshold;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("illegal arguments.");
        }

        threshold = new double[T];
        this.N = N;
        this.T = T;
        for (int i = 0; i < T; ++i) {
            per = pf.make(N);
            while (!per.percolates()) {
                int randomRow = StdRandom.uniform(N);
                int randomCol = StdRandom.uniform(N);
                per.open(randomRow, randomCol);
            }
            threshold[i] = 1.0 * per.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
