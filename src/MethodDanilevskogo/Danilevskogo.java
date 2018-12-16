package MethodDanilevskogo;

import common.Matrices;
import common.MyException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Danilevskogo {

    private int size;
    private double[][] originMatrix;
    private double[][] frobeniusMatrix;

    public Danilevskogo(int size) {
        init(size);
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        frobeniusMatrix = new double[size][size];
    }

    public Danilevskogo() {
        this(1);
    }

    public void input() throws IOException {

        try (Scanner sc = new Scanner(new File("src/matrix.txt"))) {

            size = Integer.parseInt(sc.next());
            init(size);

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    originMatrix[i][j] = Double.parseDouble(sc.next());
                }
            }
        }
    }

    public void makeFrobeniusMatrix() throws MyException {

        double[] matrixM = new double [size];
        double[][] matrixB = new double[size][size];

        double[][] tmp = Matrices.transposition(originMatrix);
        frobeniusMatrix = Matrices.multiple(tmp, originMatrix);

        Matrices.show(frobeniusMatrix);

        for (int k = size - 1; k > 0; --k) {

            for (int i = 0; i < size; ++i) {

                if (i == (k - 1)) {
                    matrixM[i] = 1 / frobeniusMatrix[k][k - 1];
                } else {
                    matrixM[i] = -(frobeniusMatrix[k][i] / frobeniusMatrix[k][k - 1]);
                }
            }

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    if (j != (k - 1)) {
                        matrixB[i][j] = frobeniusMatrix[i][j] + frobeniusMatrix[i][k - 1] * matrixM[j];
                    } else {
                        matrixB[i][j] = frobeniusMatrix[i][j] * matrixM[j];
                    }
                }
            }

            for (int i = 0; i < size; ++i) {
                if (i != (k - 1)) {
                    frobeniusMatrix[i] = matrixB[i].clone();
                }
            }

            double tmpSum = 0;
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    tmpSum += frobeniusMatrix[k][j] * matrixB[j][i];
                }
                frobeniusMatrix[k - 1][i] = tmpSum;
                tmpSum = 0;
            }
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
}
