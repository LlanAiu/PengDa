package com.llan.mahjongfunsies.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class NumericMatrix {
    //indexed as matrix[row][column]
    private final double[][] matrix;

    public NumericMatrix(int rows, int columns){
        matrix = new double[rows][columns];
    }

    public NumericMatrix(int rows, int columns, double minRandomValue, double maxRandomValue){
        this(rows, columns);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                matrix[i][j] = MathUtil.random(minRandomValue, maxRandomValue);
            }
        }
    }

    public void setValue(double value, int row, int column){
        matrix[row][column] = value;
    }

    public void setRow(double[] values, int row){
        for(int i = 0; i < matrix[row].length; i++){
            matrix[row][i] = values[i];
        }
    }

    public void setRow(int[] values, int row, int start){
        for(int i = 0; i < values.length; i++){
            matrix[row][i + start] = values[i];
        }
    }

    public void update(Function<Double, Double> function, int row, int column){
        matrix[row][column] = function.apply(matrix[row][column]).doubleValue();
    }

    public double getValue(int row, int column){
        return matrix[row][column];
    }

    public double[] getRow(int row){
        return Arrays.copyOf(matrix[row], matrix[row].length);
    }

    public int rows(){
        return matrix.length;
    }

    public int columns(){
        return matrix[0].length;
    }

    public NumericMatrix plus(NumericMatrix other){
        if(other.rows() != this.rows() || other.columns() != this.columns()){
            throw new UnsupportedOperationException("Cannot add matrices of different size!");
        }
        NumericMatrix result = new NumericMatrix(this.rows(), this.columns());
        for(int row = 0; row < result.rows(); row++){
            for(int col = 0; col < result.columns(); col++){
                result.setValue(this.getValue(row, col) + other.getValue(row, col), row, col);
            }
        }
        return result;
    }

    //this - other
    public NumericMatrix minus(NumericMatrix other){
        if(other.rows() != this.rows() || other.columns() != this.columns()){
            throw new UnsupportedOperationException("Cannot subtract matrices of different size!");
        }
        NumericMatrix result = new NumericMatrix(this.rows(), this.columns());
        for(int row = 0; row < result.rows(); row++){
            for(int col = 0; col < result.columns(); col++){
                result.setValue(this.getValue(row, col) - other.getValue(row, col), row, col);
            }
        }
        return result;
    }

    //this * other can be thought of as this(other),
    public NumericMatrix times(NumericMatrix other){
        if(this.columns() != other.rows()){
            throw new UnsupportedOperationException("Dimensional mismatch for matrix multiplication!");
        }
        NumericMatrix result = new NumericMatrix(this.rows(), other.columns());
        for(int row = 0; row < result.rows(); row++){
            for(int col = 0; col < result.columns(); col++){
                double sum = 0;
                for(int i = 0; i < this.columns(); i++){
                    sum += this.getValue(row, i) * other.getValue(i, col);
                }
                result.setValue(sum, row, col);
            }
        }
        return result;
    }

    public NumericMatrix scale(double value){
        NumericMatrix result = new NumericMatrix(this.rows(), this.columns());
        for(int row = 0; row < result.rows(); row++){
            for(int col = 0; col < result.columns(); col++){
                result.setValue(this.getValue(row, col) * value, row, col);
            }
        }
        return result;
    }

    public NumericMatrix transpose(){
        NumericMatrix result = new NumericMatrix(this.columns(), this.rows());
        for(int row = 0; row < this.rows(); row++){
            for(int col = 0; col < this.columns(); col++){
                result.setValue(this.getValue(row, col), col, row);
            }
        }
        return result;
    }

    public double convertToDouble(){
        if(this.columns() != 1 || this.rows() != 1){
            throw new UnsupportedOperationException("Cannot convert multidimensional matrix to a double");
        }
        return matrix[0][0];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("{");
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                sb.append(MathUtil.round(this.getValue(row, col), 2)).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("}").append(System.getProperty("line.separator")).append("{");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public String getDataString(){
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                sb.append(this.getValue(row, col)).append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            sb.append(System.getProperty("line.separator"));
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public static NumericMatrix parseDataString(List<String> strings){
        if(strings.isEmpty()){
            return null;
        }
        String[] string1 = strings.getFirst().split(",");
        NumericMatrix result = new NumericMatrix(strings.size(), string1.length);
        for(int i = 0; i < strings.size(); i++){
            String[] numbers = strings.get(i).split(",");
            for(int j = 0; j < numbers.length; j++){
                result.setValue(Double.valueOf(numbers[j]).doubleValue(), i, j);
            }
        }
        return result;
    }

    public static NumericMatrix empty(){
        return new NumericMatrix(0, 0);
    }

    public boolean isEmpty(){
        return rows() == 0 && columns() == 0;
    }
}
