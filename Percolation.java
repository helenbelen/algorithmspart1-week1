/* *****************************************************************************
 *  Name: Helen
 *  Date: May 16, 2020
 *  Description: Week 1 Assignment - Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.QuickUnionUF;

public class Percolation {
    private QuickUnionUF quickUnionUF;
    private int virtualTopSite;
    private int virtualBottomSite;
    private int size;
    private int N;
    private int openSites;

    public Percolation(int n) {
        N = n;
        size = (n * n);
        openSites = 0;
        quickUnionUF = new QuickUnionUF(size + 2);
        virtualTopSite = size; // equal to index of size +1
        virtualBottomSite = size + 1; //equl to index of size + 2
        int virtualSite = virtualBottomSite;
        for (int i = size - 1; i >= 0; i--) {
            quickUnionUF.union(i, virtualSite);
            openSites--;
            if (i == size - n) {//move pointer to first row
                i = (n - 1);
                virtualSite = virtualTopSite;

            }
        }

    }

    public void open(int row, int col) {
        int index = getGridIndex(row, col);

        int rowAbove = getGridIndex(row + 1, col);
        if (isOpen(rowAbove) && !quickUnionUF.connected(index, rowAbove)) {
            quickUnionUF.union(index, rowAbove);
            openSites--;
        }

        int rowBelow = getGridIndex(row - 1, col);
        if (isOpen(rowBelow) && !quickUnionUF.connected(index, rowBelow)) {
            quickUnionUF.union(index, rowBelow);
            openSites--;
        }

        int columnToLeft = getGridIndex(row, col - 1);
        if (isOpen(columnToLeft) && !quickUnionUF.connected(index, columnToLeft)) {
            quickUnionUF.union(index, columnToLeft);
            openSites--;
        }


        int columnToRight = getGridIndex(row, col + 1);
        if (isOpen(columnToRight) && !quickUnionUF.connected(index, columnToRight)) {
            quickUnionUF.union(index, columnToRight);
            openSites--;
        }

    }

    public boolean isOpen(int row, int col) {
        int index = getGridIndex(row, col);
        return quickUnionUF.find(index) != index;

    }

    private boolean isOpen(int p) {
        return quickUnionUF.find(p) != p;
    }


    public boolean isFull(int row, int col) {
        int index = getGridIndex(row, col);
        return quickUnionUF.find(index) == index;
    }

    private int getGridIndex(int row, int col) {
        if (!isParamValid(row) || !isParamValid(col)) throw new IllegalArgumentException();
        if (N % col == 0) return (row * N) - 1;
        return col + (N * (row - 1)) - 1;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return quickUnionUF.connected(virtualTopSite, virtualBottomSite);
    }

    private boolean isParamValid(int param) {
        return param > 0 && param < size;
    }

    // public static void main(String[] args) {
    //
    // }
}
