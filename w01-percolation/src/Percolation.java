/**
 * Created by SkyAo on 2017/1/9.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF backwashChecked;
    private int gridSize;
    private final int startPoint;
    private final int endPoint;
    private boolean[][] sites;
    private int openSiteNumber;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n * n + 2); // uf with source and destination
        backwashChecked = new WeightedQuickUnionUF(n * n + 1); // uf without destination

        sites = new boolean[n][n]; // grid
        openSiteNumber = 0; // initialize grid with blocked nodes

        startPoint = 0; // start from source node
        endPoint = n * n + 1; // stop with destination node
        gridSize = n; // store grid size for easier reuse
    }

    private int getFlattenId(int row, int col) { // method to flatten 2D grid matrix to 1D array
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) throw new IndexOutOfBoundsException();
        return (row - 1) * gridSize + col; // e.g. (1,1) -> 1, (1,2) -> 2, ..., (n,n) -> n**2
    }

    public void open(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) throw new IndexOutOfBoundsException();
        if (isOpen(row, col)) return;

        sites[row - 1][col - 1] = true; // open site
        openSiteNumber++; // increment count of open sites

        if (row == 1) { // top sites have to be joined with source node
            uf.union(getFlattenId(row, col), startPoint);
            backwashChecked.union(getFlattenId(row, col), startPoint);
        }

        if (row == gridSize) { 
            uf.union(getFlattenId(row, col), endPoint); // bottom sites have to be joined with destination node
        }

        if (row > 1 && isOpen(row - 1, col)) { 
            uf.union(getFlattenId(row - 1, col), getFlattenId(row, col));
            backwashChecked.union(getFlattenId(row - 1, col), getFlattenId(row, col)); // top row
        }

        if (row < gridSize && isOpen(row + 1, col)) { 
            uf.union(getFlattenId(row + 1, col), getFlattenId(row, col));
            backwashChecked.union(getFlattenId(row + 1, col), getFlattenId(row, col)); // bottom row
        }

        if (col > 1 && isOpen(row, col - 1)) { 
            uf.union(getFlattenId(row, col - 1), getFlattenId(row, col));
            backwashChecked.union(getFlattenId(row, col - 1), getFlattenId(row, col)); // left row
        }

        if (col < gridSize && isOpen(row, col + 1)) { 
            uf.union(getFlattenId(row, col + 1), getFlattenId(row, col));
            backwashChecked.union(getFlattenId(row, col + 1), getFlattenId(row, col)); // right row
        }
    }

    public boolean isOpen(int row, int col) { 
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) throw new IndexOutOfBoundsException();
        return sites[row - 1][col - 1]; // check boolean array
    }

    public boolean isFull(int row, int col) { 
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) throw new IndexOutOfBoundsException();
        return backwashChecked.connected(getFlattenId(row, col), startPoint); // a site can be full iff it is connected with source node
    }

    public int numberOfOpenSites() {
        return openSiteNumber; // O(1) lookup
    }

    public boolean percolates() {
        return numberOfOpenSites() > 0 && uf.connected(startPoint, endPoint); // the grids percolates iff source and destinations are connected while at least one cell is open
    }

    public static void main(String[] args) {
        int gridSize = StdIn.readInt(); // read grid size from stdin
        int row, col, temp;
        Percolation percolation = new Percolation(gridSize); // instantiate new object
        while (!StdIn.isEmpty()) { // loop until no more input is given
            row = StdIn.readInt(); // read row of item
            col = StdIn.readInt(); // read col of item
            percolation.open(row, col); // open item of given coords
        }

        StdOut.print(percolation.percolates()); // print whether the grid percolates
    }
}