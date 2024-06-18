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

    public void setValue(double value, int row, int column){
        matrix[row][column] = value;
    }

    public void setRow(double[] values, int row){
        for(int i = 0; i < matrix[row].length; i++){
            if(values.length > i){
                matrix[row][i] = values[i];
            }
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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("{");
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[row].length; col++){
                sb.append(this.getValue(row, col)).append(", ");
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
}
