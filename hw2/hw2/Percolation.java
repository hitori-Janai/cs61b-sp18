package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    /**
     * 共计 N*N+2个grid,第N*N为 上位点,第N*N+1(最后一个)为下位点
     */
    private boolean[][] grid;
    private int topSite;
    private int bottomSite;
    private WeightedQuickUnionUF uF;
    //用来避免backwash的UF
    private WeightedQuickUnionUF uFwithoutBackWash;
    private int numberOfOpenSites;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must > 0");
        }
        this.N = N;
        this.numberOfOpenSites = 0;
        uF = new WeightedQuickUnionUF(N * N + 2);
        topSite = N * N;
        bottomSite = N * N + 1;
        uFwithoutBackWash = new WeightedQuickUnionUF(N * N + 1);
        grid = new boolean[N][N];
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException("row and col must between 0 and N-1");
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        numberOfOpenSites++;
        connectAround(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException("row and col must between 0 and N-1");
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException("row and col must between 0 and N-1");
        }
        return uFwithoutBackWash.connected(xyTo1D(row, col), topSite);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uF.connected(bottomSite, topSite);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        for (String string : args) {
            System.out.println(string);
        }
        System.out.println("Hello");
    }

    /**
     * 返回二维grid的序号
     * @param row [0,N-1]
     * @param col [0,N-1]
     * @return [0,N*N-1]
     */
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    /**
     * 连接四周的open grid
     * @param row
     * @param col
     */
    private void connectAround(int row, int col) {
        // ↓👇,→👉,↑👆,←👈,(x,y)
        int[][] next = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int[] is : next) {
            //(x,y)先横再竖 (row,col) 先竖在横  全排列不在意顺序
            int nextGridRow = row + is[1];
            int nextGridCol = col + is[0];
            if (nextGridCol < 0 || nextGridCol >= N) {
                // 左右越界,什么都不做
            } else if (nextGridRow == -1) {
                uF.union(xyTo1D(row, col), topSite);
                uFwithoutBackWash.union(xyTo1D(row, col), topSite);
            } else if (nextGridRow == N) {
                uF.union(xyTo1D(row, col), bottomSite);
            } else if (isOpen(nextGridRow, nextGridCol)) {
                uF.union(xyTo1D(row, col), xyTo1D(nextGridRow, nextGridCol));
                uFwithoutBackWash.union(xyTo1D(row, col), xyTo1D(nextGridRow, nextGridCol));
            }
        }

    }
}
