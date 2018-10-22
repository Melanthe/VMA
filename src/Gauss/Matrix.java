package Gauss;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Matrix {

    private int size;
    private double[][] originMatrix;
    private double[] freeTerms;

    private double[][] resultMatrix;
    private double[] resultFreeTerms;
    private double[] solutions;

    private double[][] inverseMatrix;
    double[][] unitMatrix;

    private double[] vDiscrepancy;
    private double[][] mDiscrepancy;

    private double det;

    private boolean sign;


    public Matrix(int size) {

        this.size = size;
        init(size);
        sign = false;
        det = 1;
    }

    private void init(int size) {

        originMatrix = new double[size][size];
        freeTerms = new double[size];
        resultMatrix = new double[size][size];
        resultFreeTerms = new double[size];
        inverseMatrix = new double[size][size];
        solutions = new double[size];
        unitMatrix = new double[size][size];
    }

    public Matrix() {

        this(0);
    }

    public void input() throws IOException {

        try (Scanner sc = new Scanner(new File("src/matrix.txt")).useDelimiter("[\\r\\n ]+")) {

            size = Integer.parseInt(sc.next());
            init(size);

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    originMatrix[i][j] = Double.parseDouble(sc.next());
                    resultMatrix[i][j] = originMatrix[i][j];
                }
                freeTerms[i] = Double.parseDouble(sc.next());
                resultFreeTerms[i] = freeTerms[i];
            }

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; j++) {

                    if(i == j) {
                        unitMatrix[i][j] = 1;
                    } else {
                        unitMatrix[i][j] = 0;
                    }
                }
            }


            for (int i = 0; i < size; i++) {
                System.arraycopy(unitMatrix[i], 0, inverseMatrix[i], 0, size);
            }
        }
    }

    public void forwardElimination() {

        double lead;
        double tmp;

        for (int step = 0; step < size; ++step) {

            swapLines(step, findMaxLine(step));
            lead = resultMatrix[step][step];

            resultFreeTerms[step] /= lead;

            for (int i = step; i < size; ++i) {
                resultMatrix[step][i] /= lead;
            }

            for (int i = 0; i < size; ++i) {
                inverseMatrix[step][i] /= lead;
            }

            for (int i = step + 1; i < size; ++i) {
                tmp = resultMatrix[i][step];
                for (int j = step; j < size; ++j) {
                    resultMatrix[i][j] -= tmp * resultMatrix[step][j];
                }
                resultFreeTerms[i] -= tmp * resultFreeTerms[step];

                for (int k = 0; k < size; ++k) {
                    inverseMatrix[i][k] -= tmp * inverseMatrix[step][k];
                }
            }
            det *= lead;
        }

        if (sign) {
            det *= -1;
        }
    }

    public void backElimination() {

        double sum = 0;
        double[] sumInverse = new double[size];
        double[][] inverseSolutions = new double[size][size];

        for (int i = size - 1; i >= 0; i--) {

            for (int j = i + 1; j < size; j++) {

                sum += resultMatrix[i][j] * solutions[j];

                for (int k = 0; k < size; ++k) {
                    sumInverse[k] += resultMatrix[i][j] * inverseSolutions[j][k];
                }
            }

            solutions[i] = resultFreeTerms[i] - sum;
            sum = 0;

            for (int k = 0; k < size; ++k) {
                inverseSolutions[i][k] = inverseMatrix[i][k] - sumInverse[k];
            }
            for (int k = 0; k < size; ++k) {
                sumInverse[k] = 0;
            }
        }

        inverseMatrix = inverseSolutions;
    }

    private int findMaxLine(int step) {

        double max = Math.abs(resultMatrix[step][step]);
        int line = step;

        for (int i = step; i < size; ++i) {
            if (Math.abs(resultMatrix[i][step]) > max) {
                max = Math.abs(resultMatrix[i][step]);
                line = i;
            }
        }

        return line;
    }

    private void swapLines(int x, int y) {

        if (x != y) {

            double[] tmp = resultMatrix[x];
            double tmpFreeTerms = resultFreeTerms[x];

            resultMatrix[x] = resultMatrix[y];
            resultMatrix[y] = tmp;

            resultFreeTerms[x] = resultFreeTerms[y];
            resultFreeTerms[y] = tmpFreeTerms;

            tmp = inverseMatrix[x];
            inverseMatrix[x] = inverseMatrix[y];
            inverseMatrix[y] = tmp;

            sign = !sign;
        }
    }

    private double[][] matrixMultiple(double[][] x, double[][] y) throws Exception {

        if (x[0].length == y.length) {

            double[][] res = new double[x.length][y[0].length];

            for (int i = 0; i < x.length; ++i) {
                for (int j = 0; j < y[0].length; ++j) {
                    for (int k = 0; k < y.length; ++k) {
                        res[i][j] += x[i][k] * y[k][j];
                    }
                }
            }

            return res;

        } else {
            throw new Exception("Incorrect matrix for multiplication!");
        }
    }

    private double[] matrixAndVectorMultiple(double[][] x, double[] y) throws Exception {

        if (x[0].length == y.length) {

            double[] res = new double[x.length];

            for (int i = 0; i < x.length; ++i) {
                for (int j = 0; j < x[0].length; ++j) {
                    res[i] += x[i][j] * y[j];
                }
            }

            return res;

        } else {
            throw new Exception("Incorrect matrix for multiplication!");
        }
    }

    private double[] minusVectors(double[] x, double[] y) throws Exception {

        if (x.length == y.length) {

            double[] res = new double[x.length];

            for (int i = 0; i < x.length; i++) {

                res[i] = x[i] - y[i];
            }

            return res;

        } else {
            throw new Exception("Incorrect vector for minus!");
        }
    }

    private double[][] minusMatrix(double[][] x, double[][] y) throws Exception {

        if ((x.length == y.length) && (x[0].length == y[0].length)) {

            double[][] res = new double[x.length][x.length];

            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x.length; j++) {

                    res[i][j] = x[i][j] - y[i][j];
                }
            }

            return res;

        } else {
            throw new Exception("Incorrect vector for minus!");
        }
    }

    private void discrepancyVector() throws Exception {

        vDiscrepancy = minusVectors(matrixAndVectorMultiple(originMatrix, solutions), freeTerms);
    }

    private void discrepancyMatrix() throws Exception {

        mDiscrepancy = minusMatrix(matrixMultiple(originMatrix, inverseMatrix), unitMatrix);
    }

    private void showFullMatrix(double[][] matrix, double[] vector) {

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {

                System.out.printf("%8.5f ", matrix[i][j]);
            }

            System.out.printf(" | %8.5f\n", vector[i]);
        }

        System.out.println();
    }

    private void showVector(double[] vector) {

        for (double item : vector) {

            System.out.printf("%8.5e ", item);
        }
        System.out.println();
    }

    public void showOriginal() {
        System.out.println("Original matrix: \n");
        showFullMatrix(originMatrix, freeTerms);
    }

    public void showResultMatrix() {
        System.out.println("Result matrix: \n");
        showFullMatrix(resultMatrix, resultFreeTerms);
    }

    public void showSolution() {
        System.out.println("Solution: \n");

        for (double item : solutions) {

            System.out.printf("%8.5f ", item);
        }
        System.out.println();
    }

    public void showDet() {

        System.out.printf("\nDeterminant:\n\n%.5f \n\n", det);
    }

    public void showInverseMatrix() {

        System.out.println("\nInverse matrix: \n");

        for (double[] row : inverseMatrix) {
            for (double item : row) {
                System.out.printf("%8.5f ", item);
            }
            System.out.println();
        }

        System.out.println();
    }

    public void showDiscrepancyVector() throws Exception {

        discrepancyVector();

        System.out.println("Discrepancy vector: \n");
        showVector(vDiscrepancy);
    }

    public void showDiscrepancyMatrix() throws Exception {

        discrepancyMatrix();

        System.out.println("Discrepancy matrix: \n");

        for (double[] row : mDiscrepancy) {
            for (double item : row) {
                System.out.printf("%12.5e ", item);
            }
            System.out.println();
        }
    }

    public void vectorRate() {

        double rate = Math.abs(vDiscrepancy[0]);

        for (int i = 0; i < size; ++i) {
            if (Math.abs(vDiscrepancy[i]) > rate) {
                rate = Math.abs(vDiscrepancy[i]);
            }
        }

        System.out.printf("\nRate of discrepancy vector: \n%.5e\n", rate);
    }

    public void matrixRate() {

        double max = 0.0;
        double rate = Math.abs(mDiscrepancy[0][0]);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {

                if(Math.abs(mDiscrepancy[i][j]) > max) {
                    max = Math.abs(mDiscrepancy[i][j]);
                }
            }

            if (max > rate) {
                rate = max;
            }

            max = 0.0;
        }

        System.out.printf("\nRate of discrepancy matrix: \n%.5e\n", rate);
    }
}
