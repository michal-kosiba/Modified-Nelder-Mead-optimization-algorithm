package optimization.utilities;

import java.util.Arrays;

public class Matrix {
    private double[][] elements;
    private int columns;
    private int rows;

    public Matrix(int columns, int rows){
        this.elements = new double[columns][rows];
        this.columns = columns;
        this.rows = rows;
    }
    public Matrix(double[][] matrix){
        this.elements = matrix;
        this.columns = matrix[0].length;
        this.rows = matrix.length;
    }

    public Matrix copy(){
        double[][] copiedMatrix = new double[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                copiedMatrix[i][j] = elements[i][j];
            }
        }
        return new Matrix(copiedMatrix);
    }

    public static Matrix identityMatrix(int size){
        Matrix identityMatrix = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            identityMatrix.elements[i][i] = 1.0;
        }
        return identityMatrix;
    }

    public static Matrix multiply(Matrix first, Matrix second){

        if(first.getColumns() != second.getRows()){
            System.out.printf("Matrices dimensions don't match! first = [%d][%d], second = [%d][%d]", first.getRows(), first.getColumns(), second.getRows(), second.getColumns());
            System.exit(-1);
        }
        double[][] A = first.getElements();
        double[][] B = second.getElements();
        double[][] C = new double[first.rows][second.columns];

        for (int i = 0; i < first.rows; i++) {
            for (int j = 0; j < second.columns; j++) {
                for (int k = 0; k < first.columns; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return new Matrix(C);
    }

    public Vector getColumn(int column){
        if(column >= columns){
            System.out.printf("Out of border: requested column %d while there are %d columns", column, columns);
            System.exit(-1);
        }
        double[] columnArray = new double[rows];
        for (int i = 0; i < rows; i++) {
            columnArray[i] = elements[i][column];
        }
        return new Vector(columnArray);
    }

    public void setColumn(int columnNumber, Vector columnVector){
        if(columnNumber >= columns || columns < 0){
            System.out.printf("Out of border: requested column %d while there are %d columns", columnNumber, columns);
            System.exit(-1);
        }
        if(columnVector.getDimensions() > rows || rows < 0){
            System.out.printf("Out of border: to add an array of %d components to a matrix with %d rows", columnVector.getDimensions(), rows);
            System.exit(-1);
        }
        for (int i = 0; i < rows; i++) {
            elements[i][columnNumber] = columnVector.getComponent(i);
        }
    }

    public void setRow(int rowNumber, Vector rowVector){
        if(rowNumber >= rows || rows < 0){
            System.out.printf("Out of border: requested row %d while there are %d rows", rowNumber, rows);
            System.exit(-1);
        }
        if(rowVector.getDimensions() > columns || columns < 0){
            System.out.printf("Out of border: to add an array of %d components to a matrix with %d columns", rowVector.getDimensions(), columns);
            System.exit(-1);
        }
        elements[rowNumber] = rowVector.getComponents();
    }

    public void setElement(int row, int column, double value){
        if(row >= rows || rows < 0){
            System.out.printf("Out of border: requested row %d while there are %d rows", row, rows);
            System.exit(-1);
        }
        if(column >= columns || columns < 0){
            System.out.printf("Out of border: requested column %d while there are %d columns", column, columns);
            System.exit(-1);
        }
        elements[row][column] = value;
    }

    public double getElement(int row, int column){
        if(row >= rows || rows < 0){
            System.out.printf("Out of border: requested row %d while there are %d rows", row, rows);
            System.exit(-1);
        }
        if(column >= columns || columns < 0){
            System.out.printf("Out of border: requested column %d while there are %d columns", column, columns);
            System.exit(-1);
        }
        return elements[row][column];
    }

    public Vector getRow(int row){
        if(row >= rows || rows < 0){
            System.out.printf("Out of border: requested row %d while there are %d rows", row, rows);
            System.exit(-1);
        }
        double[] rowArray = new double[columns];
        System.arraycopy(elements[row], 0, rowArray, 0, columns);
        return new Vector(rowArray);
    }

    public Matrix transpose(){
        double[][] transposedElements = new double[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposedElements[j][i] = elements[i][j];
            }
        }
        return new Matrix(transposedElements);
    }

    public double[][] getElements(){
        double[][] copiedElements = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                copiedElements[i][j] = elements[i][j];
            }
        }
        return copiedElements;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append(Arrays.toString(elements[i]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
