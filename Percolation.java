/* *****************************************************************************
 *  Name: Helen
 *  Date: May 16, 2020
 *  Description: Week 1 Assignment - Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF quickUnionUF;
    private WeightedQuickUnionUF quickUnionUFTwo;
    private byte[] nodes;
    private int virtualTopSite;
    private int virtualBottomSite;
    private int openSites;
    private final int size;
    private final int totalNumberOfSites;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();
        size = n;
        totalNumberOfSites = n * n;
        virtualTopSite = totalNumberOfSites;
        virtualBottomSite = totalNumberOfSites + 1;
        quickUnionUF = new WeightedQuickUnionUF(totalNumberOfSites + 1);
        quickUnionUFTwo = new WeightedQuickUnionUF(totalNumberOfSites + 2);
        nodes = new byte[totalNumberOfSites];
    }

    public void open(int row, int col) {
        if (!isRowColValid(row) || !isRowColValid(col)) throw new IllegalArgumentException();
        int index = getGridIndex(row, col);

        if (!isOpen(index)) {
            setOpen(index);
            openSites++;
        }

        if (!isOnLeftEdge(index) && isIndexValid(index - 1) && isOpen(index - 1)) {
           connectNodes(index - 1, index);
        }
        if (!isOnRightEdge(index) && isIndexValid(index + 1) && isOpen(index + 1)) {
            connectNodes(index + 1, index);
        }
        if (isIndexValid(index - size) && isOpen(index - size)) {
            connectNodes(index - size, index);
        }
        if (isIndexValid(index + size) && isOpen(index + size)) {
            connectNodes(index + size, index);

        }
        if (isOnTop(index)) {
            quickUnionUF.union(index, virtualTopSite);
            quickUnionUFTwo.union(index, virtualTopSite);
            setConnectedToTop(index);

        }
        if (isOnBottom(index)) {
            quickUnionUFTwo.union(index, virtualBottomSite);
            setConnectedToBottom(index);
        }

    }

    public boolean isOpen(int row, int col) {
        if (!isRowColValid(row) || !isRowColValid(col)) throw new IllegalArgumentException();
        int index = getGridIndex(row, col);
        return isOpen(index);
    }

    public boolean isFull(int row, int col) {
        if (!isRowColValid(row) || !isRowColValid(col)) throw new IllegalArgumentException();
        int index = getGridIndex(row, col);
        return isOpen(index) && isNodeFull(index);

    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return quickUnionUFTwo.connected(virtualTopSite, virtualBottomSite);

    }

    private boolean isNodeFull(int index) {
        if (nodes[index] == 5) return true;
        else if (nodes[index] == 7) return true;
        else if (quickUnionUF.connected(index, virtualTopSite)) return true;
        return false;
    }

    private boolean isOpen(int index) {
        return nodes[index] % 2 != 0;
    }

    private void setOpen(int index) {
        int open = 1;
        int result = nodes[index] | open;
        nodes[index] = (byte) result;
    }

    private void setConnectedToTop(int index) {
        int connected = 4;
        int result = nodes[index] | connected;
        nodes[index] = (byte) result;
    }

    private void setConnectedToBottom(int index) {
        int connected = 2;
        int result = nodes[index] | connected;
        nodes[index] = (byte) result;
    }
    private void connectNodes(int indexOne, int indexTwo) {
        quickUnionUFTwo.union(indexOne, indexTwo);
        quickUnionUF.union(indexOne, indexTwo);
    }

    private boolean isOnLeftEdge(int index) {
        return index == 0 || index % size == 0;
    }

    private boolean isOnRightEdge(int index) {
        return (index + 1) % size == 0;
    }
    private boolean isOnTop(int index) {
        return index < size;
    }

    private boolean isOnBottom(int index) {
        return index < totalNumberOfSites && index >= totalNumberOfSites - size;
    }

    private int getGridIndex(int row, int col) {
        return col + (size * (row - 1)) - 1;
    }

    private boolean isRowColValid(int rowCol) {
        return rowCol > 0 && rowCol <= size;
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < totalNumberOfSites;
    }


}
