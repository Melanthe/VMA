package RotationMethod;

import common.Matrices;
import common.MyException;
import common.Vectors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

 public class Rotation {

    private int size;
    private double[][] originMatrix;
    private double[][] workMatrix;
    private double[][] eigenVectors;
    private double[][] discrepancyVectors;
    private final double EPSILON;

    public Rotation(int size) {
        init(size);
        EPSILON = Math.pow(10, -1);
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        workMatrix = new double[size][size];
        eigenVectors = Matrices.getUnitMatrix(size);
        discrepancyVectors = new double[size][size];
    }

    public Rotation() {this(1);}

    public void input() throws IOException, MyException {

        try (Scanner sc = new Scanner(new File("src/matrix.txt"))) {

            size = Integer.parseInt(sc.next());
            init(size);

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    originMatrix[i][j] = Double.parseDouble(sc.next());
                }
            }

           // workMatrix = Matrices.multiple(Matrices.transposition(originMatrix), originMatrix);
            workMatrix = Matrices.multiple(originMatrix, Matrices.getUnitMatrix(size));
        }
    }

    public void method() throws MyException {

        int maxL;
        int maxC;
        double cos;
        double sin;
        double tmpCos;
        double tmpTg;
        double x;
        double y;
        double[][] rotationMatrix;
        double[][] matrixB = new double[size][size];

        int count = 0;

        while(true) {
//Удалить
            System.out.printf("Step %d\n\n", count + 1);

            maxL = checkProximityMeasure();
            if (maxL == -1) {
                break;
            }
            maxC = findMax(maxL);

            tmpTg = (2 * workMatrix[maxL][maxC]) / (workMatrix[maxL][maxL] - workMatrix[maxC][maxC]);
            tmpCos = 1 / (Math.sqrt(1 + Math.pow(tmpTg, 2)));
            cos = Math.sqrt((1 + tmpCos) / 2);
            sin = Math.sqrt((1 - tmpCos) / 2)
                    * Math.signum(tmpTg);

            for (int i = 0; i < size; ++i) {
                matrixB[i] = workMatrix[i].clone();
            }

            for (int k = 0; k < size; ++k) {
                x = workMatrix[k][maxL];
                y = workMatrix[k][maxC];
                workMatrix[k][maxL] = matrixB[k][maxL] = x * cos + y * sin;
                workMatrix[k][maxC] = matrixB[k][maxC] = x * (-sin) + y * cos;
            }

            for (int k = 0; k < size; ++k) {
                workMatrix[maxL][k] = matrixB[maxL][k] * cos + matrixB[maxC][k] * sin;
                workMatrix[maxC][k] = matrixB[maxL][k] * (-sin) + matrixB[maxC][k] * cos;
            }

            rotationMatrix = Matrices.getUnitMatrix(size);
            rotationMatrix[maxL][maxL] = rotationMatrix[maxC][maxC] = cos;
            rotationMatrix[maxL][maxC] = -sin;
            rotationMatrix[maxC][maxL] = sin;

            eigenVectors = Matrices.multiple(eigenVectors, rotationMatrix);

            //workMatrix = Matrices.multiple(Matrices.transposition(rotationMatrix), Matrices.multiple(workMatrix, rotationMatrix));
//Удалить
            count ++;
            System.out.printf("Tg2: %f    Cos2: %f\n", tmpTg, tmpCos);
            System.out.printf("Cos: %f   Sin: %f   \nIndexes of max el: i:%d j:%d\n\nMatrix B:\n", cos, sin, maxL, maxC);
            Matrices.show(matrixB);
            System.out.println("Matrix of this step:\n");
            Matrices.show(workMatrix);
            System.out.println("Matrix V on this step:\n");
            Matrices.show(eigenVectors);
        }

        System.out.println("Count: " + count + "\n");
    }

    private int checkProximityMeasure() {

        double prox = 0.0;
        double maxSum = 0.0;
        double curSum = 0.0;
        int maxLine = 0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i != j) {
                    curSum += Math.pow(workMatrix[i][j], 2);
                }
            }

            if (Double.compare(curSum, maxSum) > 0) {
                maxSum = curSum;
                maxLine = i;
            }
            prox += curSum;
            curSum = 0.0;
        }
//Удалить
        System.out.printf("Proximity measure: %f\n\n", prox);
        if (Double.compare(prox, EPSILON) > 0) {
            return maxLine;
        } else {
            return (-1);
        }
    }

    private int findMax(int line) {

        int maxIndex = 0;
        double max = 0.0;
        for (int i = 0; i < size; ++i) {
            if (i != line) {
                if (Math.abs(workMatrix[line][i]) > max) {
                    max = Math.abs(workMatrix[line][i]);
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }

    public void showOriginMatrix() {

        System.out.println("Origin matrix: \n");
        Matrices.show(workMatrix);
    }

    public void showResultMatrix() {

        System.out.println("Result: \n");
        Matrices.showExp(workMatrix);
    }

    public void showEigenVectors() {

        System.out.println("Eigen Vectors: \n");
        Matrices.show(eigenVectors);
    }

    public void showDiscrepancy() throws MyException {

        double[][] tmpEigen = Matrices.transposition(eigenVectors);
        double[][] tmpOrigin = Matrices.multiple(Matrices.transposition(originMatrix), originMatrix);

        for (int i = 0; i < size; ++i) {
            discrepancyVectors[i] = Vectors.minus(Matrices.multipleWithVector(tmpOrigin, tmpEigen[i]),
                    Vectors.multipleWithScalar(tmpEigen[i], workMatrix[i][i]));
        }

        System.out.println("Discrepancy of engine vectors: \n");

        for (int i = 0; i < size; ++i) {
            System.out.printf("%8s     ", "x" + (i + 1));
        }
        System.out.println();
        Matrices.showExp(Matrices.transposition(discrepancyVectors));
    }
}
