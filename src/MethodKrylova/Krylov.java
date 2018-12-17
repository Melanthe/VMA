package MethodKrylova;

import Gauss.Matrix;
import common.Matrices;
import common.MyException;
import common.Vectors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Krylov {

    private int size;
    private double[][] originMatrix;
    private double[][] resultMatrix;

    private double[][] discrepancyMatrix;
    private double[] discrepancyVector;

    private double[][] eigenVectors;
    private double[] eigenValues;
    private double[] eigenPolynomial;

    private double[][] matrixC;
    private double[] vectorC;

    public Krylov(int size) {
        init(size);
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        resultMatrix = new double[size][size];
        eigenVectors = new double[size][size];
        vectorC = new double[size];
        matrixC = Matrices.getUnitMatrix(size);
        eigenValues = new double[size];
        eigenPolynomial = new double[size];
        discrepancyMatrix = new double[size][size];
        discrepancyVector = new double[size];
    }

    public Krylov() {
        this(1);
    }

    public void input() throws IOException, MyException {

        try (Scanner sc = new Scanner(new File("src/matrix.txt"))) {

            size = Integer.parseInt(sc.next());
            init(size);

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    originMatrix[i][j] = Double.parseDouble(sc.next());
                }
            }

            for (int i = 0; i < size; ++i) {
                eigenValues[i] = Double.parseDouble(sc.next());
            }

            resultMatrix = Matrices.multiple(Matrices.transposition(originMatrix), originMatrix);
        }
    }

    public void makeEigenPolynomial() throws MyException {

        for (int i = 1; i < size; ++i) {
            matrixC[i] = Matrices.multipleWithVector(resultMatrix, matrixC[i - 1]);
        }
        vectorC = Matrices.multipleWithVector(resultMatrix, matrixC[size - 1]);
    }

    public void getEigenVectors() throws MyException {

        Matrix gauss = new Matrix(Matrices.transposition(matrixC), vectorC);
        gauss.forwardElimination();
        eigenPolynomial = gauss.backElimination();

        double[][] beta = new double[size][size];

        for (int k = 0; k < size; ++k) {

            beta[k][0] = 1;

            for (int i = 1; i < size; ++i) {
                beta[k][i] = beta[k][i - 1] * eigenValues[k] - eigenPolynomial[size - i];
            }
        }

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                eigenVectors[i] = Vectors.plus(eigenVectors[i],
                        Vectors.multipleWithScalar(matrixC[size - 1 - j], beta[i][j]));
            }
        }
    }

    public void showOriginalMatrix() {

        System.out.println("Original matrix: \n");
        Matrices.show(originMatrix);
    }

    public void showChangedMatrix() {

        System.out.println("Symmetric: \n");
        Matrices.show(resultMatrix);
    }

    public void showMatrixC() {

        System.out.println("Matrix C: \n");
        Matrices.showFull(Matrices.transposition(matrixC), vectorC);
    }

    public void showEigenVectors() {

        for (int i = 0; i < size; ++i) {
            System.out.printf("%5s    ", "x" + (i + 1));
        }
        System.out.println();
        Matrices.show(Matrices.transposition(eigenVectors));
    }

    public void showDiscrepancy() throws MyException {

        Vectors.show(eigenValues);
        for (int i = 0; i < size; ++i) {
            discrepancyMatrix[i] = Vectors.minus(Matrices.multipleWithVector(resultMatrix, eigenVectors[i]),
                    Vectors.multipleWithScalar(eigenVectors[i], eigenValues[i]));
        }

        System.out.println("Discrepancy of engine vectors: \n");

        for (int i = 0; i < size; ++i) {
            System.out.printf("%8s     ", "x" + (i + 1));
        }
        System.out.println();
        Matrices.showExp(Matrices.transposition(discrepancyMatrix));

        System.out.println("Discrepancy of engine polynomial: \n");

        for(int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                discrepancyVector[i] += Math.pow(eigenValues[i], j) * (-eigenPolynomial[j]);
            }
            discrepancyVector[i] += Math.pow(eigenValues[i], size);
        }
        Vectors.showExp(discrepancyVector);
    }
}
