package MethodDanilevskogo;

import common.Matrices;
import common.MyException;
import common.Vectors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Danilevskogo {

    private int size;
    private double[][] originMatrix;
    private double[][] frobeniusMatrix;
    private double[][] eigenVectors;
    private double[] eigenValues;
    private double[][] discrepancy;
    private double[][] matrixS;

    public Danilevskogo(int size) {
        init(size);
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        frobeniusMatrix = new double[size][size];
        eigenVectors = new double[size][size];
        matrixS = Matrices.getUnitMatrix(size);
        eigenValues = new double[size];
        discrepancy = new double[size][size];
    }

    public Danilevskogo() {
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
        }

        double[][] transp = Matrices.transposition(originMatrix);
        originMatrix = Matrices.multiple(transp, originMatrix);
        for (int i = 0; i < size; ++i) {
         frobeniusMatrix[i] = originMatrix[i].clone();
        }
    }

    public void makeFrobeniusMatrix() throws MyException {

        double[] vectorM = new double [size];
        double[][] matrixB = new double[size][size];
        double[][] tmp;

         for (int k = size - 1; k > 0; --k) {

            for (int i = 0; i < size; ++i) {

                if (i == (k - 1)) {
                    vectorM[i] = 1 / frobeniusMatrix[k][k - 1];
                } else {
                    vectorM[i] = -(frobeniusMatrix[k][i] / frobeniusMatrix[k][k - 1]);
                }
            }

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    if (j != (k - 1)) {
                        matrixB[i][j] = frobeniusMatrix[i][j] + frobeniusMatrix[i][k - 1] * vectorM[j];
                    } else {
                        matrixB[i][j] = frobeniusMatrix[i][j] * vectorM[j];
                    }
                }
            }

            for (int i = 0; i < k - 1; ++i) {
                    frobeniusMatrix[i] = matrixB[i].clone();
            }

            double tmpSum = 0;
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    tmpSum += frobeniusMatrix[k][j] * matrixB[j][i];
                }
                frobeniusMatrix[k - 1][i] = tmpSum;
                tmpSum = 0;
            }

            for (int i = k; i < size; ++i) {
                frobeniusMatrix[i] = matrixB[i].clone();
            }

            tmp = Matrices.getUnitMatrix(size);
            tmp[k - 1] = vectorM.clone();

            matrixS = Matrices.multiple(matrixS, tmp);
         }
    }

    public void getEigenVectors() throws MyException {

        double[] vectorY = new double[size];

        for (int i = 0; i < size; ++i) {

            for(int j = 0; j < size; ++j) {
                vectorY[j] = Math.pow(eigenValues[i], size - 1 - j);
            }
            eigenVectors[i] = Matrices.multipleWithVector(matrixS, vectorY);
        }
    }

    public void showOriginalMatrix() {

        System.out.println("Original matrix: \n");
        Matrices.show(originMatrix);
    }

    public void showFrobeniusMatrix() {

        System.out.println("Frobenius matrix: \n");
        Matrices.show(frobeniusMatrix);
    }

    public void showEigenVectors() {

        for (int i = 0; i < size; ++i) {
            System.out.printf("%7s     ", "x" + (i + 1));
        }
        System.out.println();
        Matrices.show(Matrices.transposition(eigenVectors));
    }

    public void showDiscrepancy() throws MyException {

        for (int i = 0; i < size; ++i) {
            discrepancy[i] = Vectors.minus(Matrices.multipleWithVector(originMatrix, eigenVectors[i]),
                    Vectors.multipleWithScalar(eigenVectors[i], eigenValues[i]));
        }

        System.out.println("Discrepancy of engine vectors: \n");

        for (int i = 0; i < size; ++i) {
            System.out.printf("%8s     ", "x" + (i + 1));
        }
        System.out.println();
        Matrices.showExp(Matrices.transposition(discrepancy));
    }
}
