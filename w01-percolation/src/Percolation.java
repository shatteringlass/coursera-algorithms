import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private static enum Cell {CLOSED, EMPTY, FULL}
    private WeightedQuickUnionUF uf;
    private final Cell[][] field;
    
    private final int N;
    
    public Percolation(int n) 
    {
        // create n-by-n grid, with all sites blocked
        if (n<=0) throw new IllegalArgumentException("Invalid input: n must be > 0");
        this.N = n;
        uf = new WeightedQuickUnionUF(n*n+2);
        grid = new field[this.N*this.N];
        for (int i = 0; i < this.N; ++i)
        {
            for (int j = 0; j < this.N; ++j)
            {
                field[i][j] = Cell.CLOSED;
            }
        }
    }
    
    public void isValid(int row, int col)
    {
        if ( (row <= 0 || row >= n) || (col <= 0 || col >= n) )
        {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public void open(int row, int col) // open site (row, col) if it is not open already
    {
        if (isOpen(row,col)) return;
        field[row][col] = 'EMPTY';
        openAdjacent(row,col);
    }
    
    
    public int[][] getOpenAdjacent(int row, int col)
    {
        
    }
    
    public void openAdjacent(int row, int col)
    {
        colLeft;
        colRight;
        rowTop;
        rowBottom;
    }
    
    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        isValid(row, col);
        return this.field[row][col] <> 'CLOSED';
    }
    
    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        
    }
    
    public int numberOfOpenSites() // number of open sites
    {
        
    }
    
    public boolean percolates() // does the system percolate?
    {
        
    }
    
    public static void main(String[] args) // test client (optional)
    {
        
    }
}