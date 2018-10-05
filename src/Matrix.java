import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Matrix {

    private int size;
    private double[][] originMatrix;
    private double[] freeTerms;

    private double[][] resultMatrix;
    private double[] resultFreeTerms;
    private double[] solutions;

    private double[] vDiscrepancy;

    private double det;

    private boolean sign;


    public Matrix(int size) {

        this.size = size;
        originMatrix = new double[size][size];
        freeTerms = new double[size];
        resultMatrix = new double[size][size];
        resultFreeTerms = new double[size];
        solutions = new double[size];
        sign = false;
        det = 1;
    }

    public Matrix() {

        this(0);
    }

    public void input() {

        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        System.out.println("Input elements of the original matrix: ");

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {

                while (!sc.hasNextDouble()) {
                    System.out.println("Incorrect input!");
                    sc.next();
                }
                originMatrix[i][j] = sc.nextDouble();
                resultMatrix[i][j] = originMatrix[i][j];
            }
        }

        System.out.println("Input free terms: ");

        for (int i = 0; i < size; ++i) {

            while (!sc.hasNextDouble()) {
                System.out.println("Incorrect input!");
                sc.next();
            }
            freeTerms[i] = sc.nextDouble();
            resultFreeTerms[i] = freeTerms[i];
        }
    }

    private void showFullMatrix(double[][] matrix, double[] vector) {

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {

                System.out.printf("%.5f ", matrix[i][j]);
            }

            System.out.println(" " + vector[i]);
        }

        System.out.println();
    }

    private void showVector(double[] vector) {

        for (double item : vector) {

            System.out.printf("%.5f ", item);
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
        showVector(solutions);
    }

    public void showDet() {

        System.out.printf("\nDeterminant:\n\n%.5f \n", det);
    }

    public void forwardElimination() {

        double lead;

        for (int step = 0; step < size; ++step) {

            swapLines(step, findMaxLine(step));
            lead = resultMatrix[step][step];

            resultFreeTerms[step] /= lead;

            for (int i = step; i < size; ++i) {
                resultMatrix[step][i] /= lead;
            }

            for (int i = step + 1; i < size; ++i) {
                for (int j = step + 1; j < size; ++ j) {
                    resultMatrix[i][j] -= resultMatrix[i][step] * resultMatrix[step][j];
                }
                resultFreeTerms[i] -= resultMatrix[i][step] * resultFreeTerms[step];
                resultMatrix[i][step] = 0;
            }

            det *= lead;
        }

        if(sign) {
            det *= -1;
        }
    }

    public void backElimination() {

        double sum = 0;

        solutions[size - 1] = resultFreeTerms[size - 1];

        for (int i = size - 1; i >= 0; i --) {

            for(int j = i + 1; j < size; j++) {
                sum += resultMatrix[i][j] * resultFreeTerms[j];
            }
            solutions[i] = resultFreeTerms[i] - sum;
        }
    }

    private int findMaxLine(int step) {

        double max = resultMatrix[step][step];
        int line = step;

        for (int i = step; i < size; ++i) {
            if (resultMatrix[i][step] > max) {
                max = resultMatrix[i][step];
                line = i;
            }
        }

        return line;
    }

    private void swapLines(int x, int y) {

        if(x != y) {
            double[] tmp = resultMatrix[x];
            resultMatrix[x] = resultMatrix[y];
            resultMatrix[y] = tmp;

            sign = !sign;
        }
    }

    private void transposition(double[][] matrix) {

        for (int i = 0; i < matrix.length / 2; ++i) {
            for (int j = i; j < matrix.length; ++j) {

                double tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

    private double[][] matrixMultiple(double[][] x, double[][] y) throws Exception {

        if (x[0].length == y.length) {

            double[][] res = new double[x.length][y[0].length];

            for (int i = 0; i < x.length; ++i) {
                for (int j = 0; j < y[0].length; ++j) {
                    for (int k = 0; k < y.length; ++k) {
                        res[i][j] += x[k][i] * y[j][k];
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

            for(int i = 0; i < x.length; i++) {

                res[i] = x[i] - y[i];
            }

            return res;

        } else {
            throw new Exception("Incorrect vector for minus!");
        }
    }

    public void discrepancyVector() throws Exception{

        vDiscrepancy = minusVectors(matrixAndVectorMultiple(originMatrix, solutions), freeTerms);

        System.out.println("\nDiscrepancy vector: \n");

        for(double item : vDiscrepancy) {

            System.out.printf("%.5e ", item);
        }

        System.out.println();
    }
}
