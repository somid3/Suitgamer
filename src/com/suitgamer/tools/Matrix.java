package com.suitgamer.tools;

import org.junit.Assert;

import java.util.Arrays;

/**
 * Simple 2D matrix of integers with column and row sum This simple
 * integer matrix allows imple math functions and keeps track of the
 * column sums and row sums with every math call Note, a matrix does
 * not have a zero-th row and column. In this matrix the first row is
 * recognized with the index 1. The first column is recognized with
 * index 1. There is no such thing as a matrix row or column with
 * index 0 When this matrix is initiated all matrix elements are given
 * the default value of zero
 *
 * @author Omid Sadeghpour <somid3@gmail.com>
 * @version 1.0
 */
public class Matrix {

    /**
     * Constant {@link #ROWS} and {@link #COLUMNS} are set by the
     * constructor and define the total dimensions of the matrix
     */
    private int matrix[][];

    /**
     * Contains as many integer elements as the number of columns
     * which {@link #matrix} contains. At all times the respective
     * column index will contain a value equal to the sum of all the
     * elements in the {@link #matrix} column
     */
    private int columnSums[];

    /**
     * Contains as many integer elements as the number of rows which
     * {@link #matrix} contains. At all times the respective row index
     * will contain a value equal to the sum of all the elements in
     * the {@link #matrix} row
     */
    private int rowSums[];

    /**
     * Number of rows in matrix
     */
    private int ROWS;

    /**
     * Number of columns in matrix
     */
    private int COLUMNS;

    /**
     * Matrix constructor
     *
     * @param ROWS
     * @param COLUMNS
     */
    public Matrix(int ROWS, int COLUMNS) {
        // Setting matrix constants
        this.ROWS = ROWS;
        this.COLUMNS = COLUMNS;

        // Creating a matrix of zeros
        reset();
    }

    public int getNumberOfRows() {
        return ROWS;
    }

    public int getNumberOfColumns() {
        return COLUMNS;
    }

    /**
     * Adds a value to the specified cell
     *
     * @param row    Row index locating the cell to be altered, note the
     *               first row index is 1
     * @param column Column index locating the cell to be altered,
     *               note the first column index is 1
     * @param value  Value to add
     * @return
     */
    public void addToCell(int row, int column, int value) {

        // Mapping index values
        row = Matrix.mapMatrixIndexToArrayIndex(row);
        column = Matrix.mapMatrixIndexToArrayIndex(column);

        // Adding to cell the provided value
        matrix[row][column] += value;

        // Adding to column sum the provided value
        columnSums[column] += value;

        // Adding to row sum the provided value
        rowSums[row] += value;
    }

    /**
     * Substracts a value to the specified cell
     *
     * @param row    Row index locating the cell to be altered, note the
     *               first row index is 1
     * @param column Column index locating the cell to be altered,
     *               note the first column index is 1
     * @param value  Value to substract
     * @return
     */
    public void substractFromCell(int row, int column, int value) {

        // Mapping index values
        row = Matrix.mapMatrixIndexToArrayIndex(row);
        column = Matrix.mapMatrixIndexToArrayIndex(column);

        // Substracts from cell the provided value
        matrix[row][column] -= value;

        // Substracts from column sum the provided value
        columnSums[column] -= value;

        // Substracts from row sum the provided value
        rowSums[row] -= value;

    }

    /**
     * Returns the 2D array that makes up the matrix
     *
     * @return
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Returns the cell value provided its location
     *
     * @param row    Row index locating the cell to be returned, note the
     *               first row index is 1
     * @param column Column index locating the cell to be returned,
     *               note the first column index is 1
     * @return
     */
    public int getCell(int row, int column) {

        // Mapping index values
        row = Matrix.mapMatrixIndexToArrayIndex(row);
        column = Matrix.mapMatrixIndexToArrayIndex(column);

        return matrix[row][column];
    }

    /**
     * Sets the cell value provided its location
     *
     * @param row    Row index locating the cell to be set, note the
     *               first row index is 1
     * @param column Column index locating the cell to be set, note
     *               the first column index is 1
     * @param value  New value of cell
     * @return
     */
    public void setCell(int row, int column, int value) {

        // Getting old cell value
        int old = getCell(row, column);

        // Calculating the difference between the new value and the
        // old value
        int difference = value - old;

        // Adding the difference
        addToCell(row, column, difference);

    }

    /**
     * Returns an integer array containing all the elements of the
     * matrix column provided the column index
     *
     * @param column Column index locating column values to be
     *               returned, note the first column index is 1
     * @return
     */
    public int[] getColumn(int column) {

        // Mapping index value
        column = Matrix.mapMatrixIndexToArrayIndex(column);

        // Setting default output array
        int[] output = new int[ROWS];

        // Looping over all rows
        for (int row = 0; row < ROWS; row++) {

            // Saving matrix element in row 'r' and column
            // 'c' to output
            output[row] = matrix[row][column];

        }

        return output;
    }

    /**
     * Returns the sum of all the elements in the specified matrix
     * column. Note that the first matrix column is 1
     *
     * @param column Index of the column whose sum should be returned
     * @return
     */
    public int getColumnSum(int column) {

        // Mapping index value
        column = Matrix.mapMatrixIndexToArrayIndex(column);

        return columnSums[column];
    }

    /**
     * Returns the sums of all the columns in the matrix
     *
     * @return
     */
    public int[] getColumnSums() {
        return columnSums;
    }

    /**
     * Returns the sum of all the elements in the specified matrix
     * row. Note that the first matrix row is 1
     *
     * @param row Index of the row whose sum should be returned
     * @return
     */
    public int getRowSum(int row) {

        // Mapping index value
        row = Matrix.mapMatrixIndexToArrayIndex(row);

        return rowSums[row];
    }

    /**
     * Returns the sums of all the rows in the matrix
     *
     * @return
     */
    public int[] getRowSums() {
        return rowSums;
    }

    /**
     * Returns an integer array containing all the elements of the
     * matrix row provided the row index
     *
     * @param row Row index locating row values to be returned, note
     *            the first row index is 1
     * @return
     */
    public int[] getRow(int row) {

        // Mapping index values
        row = Matrix.mapMatrixIndexToArrayIndex(row);

        // Retrieving required row
        int[] output = matrix[row];

        return output;
    }

    /**
     * Sets all the values in a the {@link #matrix} to zero
     *
     * @return
     */
    public void reset() {

        // Setting default the matrix variable
        matrix = new int[ROWS][COLUMNS];

        // Setting default column sums variable
        columnSums = new int[COLUMNS];

        // Setting default row sums variable
        rowSums = new int[ROWS];
    }

    /**
     * A matrix index recognizes the first column and row with index 1
     * instead of 0. An array index recognizes the first column and
     * row with index 0 and not 1. This static method maps a matrix
     * index to an array index
     *
     * @param index Matrix index to be mapped to array index
     * @return
     */
    public static int mapMatrixIndexToArrayIndex(int index) {
        return (index - 1);
    }

    /**
     * A matrix index recognizes the first column and row with index 1
     * instead of 0. An array index recognizes the first column and
     * row with index 0 and not 1. This static method maps an array
     * index to a matrix index
     *
     * @param index Array index to be mapped to matrix index
     * @return
     */
    public static int mapArrayIndexToMatrixIndex(int index) {
        return (index + 1);
    }

    /**
     * Given a 2D integer array this method translates it to a string
     * that is human readable. Each row is separated by a new line and
     * each element in the row by a space
     *
     * @return
     */
    public static String toReadableString(int[][] array) {
        // Setting default string buffer
        StringBuffer temp = new StringBuffer();

        // Looping over rows
        for (int r = 0; r < array.length; r++) {

            // Generating row readable string
            temp.append(Matrix.toReadableString(array[r]));

            // Appending row separator
            temp.append("\n");

        }

        // Creating output
        String output = temp.toString();

        return output;
    }

    /**
     * Given a 1D integer array this method translates it to a string
     * that is human readable. Each element is separated by a space
     *
     * @param array Array to be translated to a human readable string
     * @return
     */
    public static String toReadableString(int[] array) {
        return Arrays.toString(array);
    }

    /**
     * Sums all the elements of an array and returns the total
     *
     * @param array Array containting all values to be summed
     * @return Summation of all values in array
     */
    public static int sum(int[] array) {
        // Setting default output
        int output = 0;

        // Looping over elements
        for (int e = 0; e < array.length; e++) {

            // Summing values
            output += array[e];

        }

        return output;
    }

    /**
     * Returns the maximum value in an array of integers.  The array must
     * at least have one element otherwise an error is returned
     *
     * @param array
     * @return
     */
    public static int max(int[] array) {
        // Setting initial value
        int maximum = array[0];

        // Looping over all elements of the array
        for (int i = 1; i < array.length; i++) {

            // Is the current value a maximum?
            if (array[i] > maximum) {

                // Yes, set new maximum
                maximum = array[i];

            }

        }

        return maximum;
    }

    public void setMatrix (int[][] matrix) {
        this.matrix = matrix;
    }

    public void setRowSums (int[] rowSums) {
        this.rowSums = rowSums;
    }

    public void setColumnSums (int[] columnSums) {
        this.columnSums = columnSums;
    }

    public String toString() {
        return Matrix.toReadableString(matrix);
    }
}
