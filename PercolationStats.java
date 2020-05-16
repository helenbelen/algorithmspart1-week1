/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private Percolation p;
    private int N;
    public PercolationStats(int n, int trials) {
        validate(n);
        validate(trials);
        N = n * n;
        results = new double[trials];
        for(int i = 0; i < trials; i++) {
            this.runTrial(n, i);
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - (1.96*(stddev()/Math.sqrt(results.length)));
    }

    public double confidenceHi() {
        return mean() + (1.960*(stddev()/Math.sqrt(results.length)));
    }

    private int getRandomNumberToOpen(int n) {
        return StdRandom.uniform(n*n);
    }

    private int getRandomCoordinate(int n) {
        return StdRandom.uniform(n);
    }

    private void runTrial(int n, int trialNumber) {
        p = new Percolation(n);
        int trialOpen = getRandomNumberToOpen(n);
        for (int i = 0; i < trialOpen; i++){
            p.open(getRandomCoordinate(n), getRandomCoordinate(n));
            if (p.percolates()) {
                double perc = (double) p.numberOfOpenSites()/N;
                results[trialNumber] = perc;
            }
        }
    }

    private static void validate(int param) {
        if (param <= 0) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        validate(n);
        int trials = Integer.parseInt(args[1]);
        validate(trials);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean" + " " + stats.mean());
        StdOut.println("stddev" + " " + stats.stddev());
        StdOut.println("95% confidence level" + " " + "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }
}
