/**
 * Created by SkyAo on 2017/1/10.
 */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] experimentScores;

    public PercolationStats(int n, int trials) {
        int row, col; // cell coordinates
        int i; // counter

        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        Percolation[] percolations = new Percolation[trials]; // create multiple grids
        experimentScores = new double[trials]; // create score array
        for (i = 0; i < trials; i++) { // for each trial...
            percolations[i] = new Percolation(n); // ...create a n-sized grid...
            while (!percolations[i].percolates()) { // ...and while the grid does not percolate...
                row = StdRandom.uniform(1, n + 1); // ...pick a grid row at random...
                col = StdRandom.uniform(1, n + 1); // ...and pick a grid column at random...
                percolations[i].open(row, col); // ...and open it.
            }
            experimentScores[i] = 1.0 * percolations[i].numberOfOpenSites() / (n * n); // how many sites did I have to open (wrt total size of grid)?
        }
    }

    public double mean() {
        return StdStats.mean(experimentScores); // return the mean across every trial
    }

    public double stddev() {
        return StdStats.stddev(experimentScores); // return the standard deviation for the trials
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(experimentScores.length); // return the lower bound of the confidence interval (95%)
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(experimentScores.length); // return the upper bound of the confidence interval (95%)
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]); // read first argument and save as grid size
        int t = Integer.parseInt(args[1]); // read second argument and save as trials number
        PercolationStats percolationStats = new PercolationStats(n, t);
        // print trial results
        StdOut.printf("mean                    = %f\n", percolationStats.mean());
        StdOut.printf("stddev                  = %f\n", percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}