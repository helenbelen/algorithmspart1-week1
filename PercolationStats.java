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
    private final int totalNumberOfItems;
    private double mean;
    private double std;
    private static final double CONFIDENCE_95 = 1.96;
    public PercolationStats(int n, int trials) {
        validate(n);
        validate(trials);
        totalNumberOfItems = n * n;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            this.runTrial(p, n, i);
        }
    }

    public double mean() {
        mean = StdStats.mean(results);
        return mean;
    }

    public double stddev() {
        std = StdStats.stddev(results);
        return std;
    }

    public double confidenceLo() {
        return mean - ((CONFIDENCE_95 * std)/ Math.sqrt(results.length));
    }

    public double confidenceHi() {
        return mean + ((CONFIDENCE_95 * std) / Math.sqrt(results.length));
    }

    private int getRandomCoordinates(int n) {
        return StdRandom.uniform(1,n + 1);
    }

    private void runTrial(Percolation p, int n, int trialNumber) {
       while (true) {
           int randomRow = getRandomCoordinates(n);
           int randomCol = getRandomCoordinates(n);
           if (!p.isOpen(randomRow, randomCol)) {
               p.open(randomRow, randomCol);
               if (p.percolates()) {
                   double perc = (double) p.numberOfOpenSites() / totalNumberOfItems;
                   results[trialNumber] = perc;
                   break;
               }
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
        StdOut.println("95% confidence level" + " " + "[" + stats.confidenceLo() + ", " + stats
                .confidenceHi() + "]");

    }
}
