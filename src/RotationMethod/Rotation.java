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
    private double[][] resultMatrix;
    private double[] eigenVector;
    private double[] discrepancyVector;

    private double maxEigenValue;
    private final double EPSILON;

    public Rotation(int size) {
        init(size);
        EPSILON = Math.pow(10, -5);
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        resultMatrix = new double[size][size];
        eigenVector = new double[size];
        discrepancyVector = new double[size];
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

            resultMatrix = Matrices.multiple(Matrices.transposition(originMatrix), originMatrix);
        }
    }

    public void method() throws MyException {

        double[] current;
        double[] previous = Matrices.getUnitMatrix(size)[0];
        double[] next = new double[size];
        double[] raitCurrent;
        double tmp;
        boolean flag = true;

        while (flag) {

            current = Matrices.multipleWithVector(resultMatrix, previous);
            raitCurrent = Vectors.rationing(current);
            next = Matrices.multipleWithVector(resultMatrix, raitCurrent);

            flag = false;
            for (int i = 0; i < size; ++i) {
                tmp = Math.abs((next[i] / raitCurrent[i]) - (current[i] / previous[i]));
                if (tmp > EPSILON) {
                    flag = true;
                    break;
                }
            }

            previous = raitCurrent.clone();

            if (!flag) {
                maxEigenValue = next[0] / raitCurrent[0];
            }
        }

        eigenVector = next.clone();
    }

    public void showOriginMatrix() {

        System.out.println("Origin matrix: \n");
        Matrices.show(originMatrix);
    }

    public void showChangedMatrix() {

        System.out.println("Symmetric: \n");
        Matrices.show(resultMatrix);
    }

    public void showEigenValue() {

        System.out.println("Eigen value: \n " + maxEigenValue + "\n");
    }

    public void showEigenVector() {

        System.out.println("Eigen vector: \n");
        Vectors.show(eigenVector);
    }

    public void showDiscrepancy() throws MyException {

        discrepancyVector = Vectors.minus(Matrices.multipleWithVector(resultMatrix, eigenVector),
                Vectors.multipleWithScalar(eigenVector, maxEigenValue));

        System.out.println("Discrepancy vector: \n");
        Vectors.showExp(discrepancyVector);
    }
}
