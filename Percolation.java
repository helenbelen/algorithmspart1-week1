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
    private int openNumber;
    private int size;
    private int N;

    public Percolation(int n) {
        size = n;
        N = (n * n);
        quickUnionUF = new QuickUnionUF(N + 3);
        virtualTopSite = N; // equal to index of size +1
        virtualBottomSite = N + 1; //equl to index of size + 2
        openNumber =  N + 2; //higher number used to make squares as open if they aren't touching other open squares
    }

    public void open(int row, int col) {
        int index = getGridIndex(row, col);
        if (isTopRow(row) && !quickUnionUF.connected(index, virtualTopSite)) {
            quickUnionUF.union(index, virtualTopSite);
        }
        if (isBottomRow(row) && !quickUnionUF.connected(index, virtualBottomSite)) {
            quickUnionUF.union(index, virtualBottomSite);
        }
        int[] rowAbove = { row - 1, col};
        int[] rowBelow = { row + 1, col};
        int[] columnLeft = { row, col - 1 };
        int[] columnRight = { row, col + 1 };
        int [][] rowsCols = { rowAbove, rowBelow, columnLeft, columnRight };
        for(int j = 0; j < 4; j++) {
            int checkRow = rowsCols[j][0];
            int checkCol = rowsCols[j][1];
            if (isRowColValid(checkRow) && isRowColValid(checkCol)) {
                int checkIndex = getGridIndex(checkRow, checkCol);
                if (isOpenOrFull(checkIndex) && !quickUnionUF.connected(index, checkIndex)) {
                    quickUnionUF.union(index, checkIndex);
                }
            }
        }
        if (!isOpen(row, col)) { //if it's still open, set to higher number
            quickUnionUF.union(index, openNumber);
        }
    }

    public boolean isOpen(int row, int col) {
        int index = getGridIndex(row, col);
        int parent = quickUnionUF.find(index);
        return parent != index;

    }

    private boolean isOpenOrFull(int p) {
        return quickUnionUF.find(p) != p;
    }

    public boolean isFull(int row, int col) {
        int index = getGridIndex(row, col);
        int parent = quickUnionUF.find(index);
        if (parent == virtualBottomSite) {
            return percolates();
        }
        return parent != index && (parent == virtualTopSite); // parent will be less that size if it is in the top row
    }

    private int getGridIndex(int row, int col) {
        if (!isRowColValid(row) || !isRowColValid(col)) throw new IllegalArgumentException();
        return col + (size * (row - 1)) - 1;
    }

    public int numberOfOpenSites() {
        return N - (N - quickUnionUF.count());
    }

    public boolean percolates() {
        return quickUnionUF.connected(virtualTopSite, virtualBottomSite);
    }

    private boolean isParamValid(int param) {
        return param > 0 && param < N;
    }

    private boolean isRowColValid(int rowCol) {
        return rowCol > 0 && rowCol <= size;
    }

    private boolean isTopRow(int row) {
        return row == 1;
    }

    private boolean isBottomRow(int row) {
        return row == size;
    }
    // public static void main(String[] args) {
    //
    // }
}
