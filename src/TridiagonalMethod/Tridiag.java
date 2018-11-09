package TridiagonalMethod;

import common.Matrices;
import common.MyException;
import common.Vectors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tridiag {

    private int size;
    private int mid;
    private double[][] originMatrix;
    private double[] b;
    private double[] c;
    private double[] a;
    private double[] f;

    private double[] alpha;
    private double[] beta;
    private double[] xi;
    private double[] eta;

    private double[] solutions;
    private double[] discrepancy;

    private double det;

    public Tridiag(int size) {

        this.size = size;
        mid = (size - 1) / 2;
        init(size);
    }

    private void init(int size) {

        originMatrix = new double[size][size];
        b = new double[size - 1];
        c = new double[size];
        a = new double[size - 1];
        f = new double[size];
        alpha = new double[mid];
        beta = new double[mid];
        xi = new double[size - mid];
        eta = new double[size - mid];
        solutions = new double[size];
        discrepancy = new double[size];
    }

    public Tridiag() {
        this(1);
    }

    public void input() throws IOException {

        try (Scanner sc = new Scanner(new File("src/matrix.txt"))) {

            size = Integer.parseInt(sc.next());
            mid = size / 2;
            init(size);

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    originMatrix[i][j] = Double.parseDouble(sc.next());
                }
                f[i] = Double.parseDouble(sc.next());
            }

            for (int i = 0; i < size - 1; ++i) {
                b[i] = -(Double.parseDouble(sc.next()));
            }
            for (int i = 0; i < size; ++i) {
                c[i] = Double.parseDouble(sc.next());
            }
            for (int i = 0; i < size - 1; ++i) {
                a[i] = -(Double.parseDouble(sc.next()));
            }
        }
    }

    public void TridiagonalMethod() {

        initForward();
        calcBetaAndAlpha();
        calcXiAndEta();
        initBack();

        for (int i = mid - 1; i >= 0; --i) {
            solutions[i] = alpha[i] * solutions[i + 1] + beta[i];
        }

        int count = 1;
        for (int i = mid + 1; i < size; ++i) {
            solutions[i] = xi[count] * solutions[i - 1] + eta[count];
            count++;
        }
    }

    private void initForward() {

        alpha[0] = b[0] / c[0];
        beta[0] = f[0] / c[0];
        xi[size - mid - 1] = a[size - 2] / c[size - 1];
        eta[size - mid - 1] = f[size - 1] / c[size - 1];
    }

    private void initBack() {

        solutions[mid] = (xi[0] * beta[mid - 1] + eta[0]) / (1 - xi[0] * alpha[mid - 1]);
    }

    private void calcBetaAndAlpha() {

        for (int i = 1; i < mid; ++i) {

            alpha[i] = b[i] / (c[i] - alpha[i - 1] * a[i - 1]);
            beta[i] = (f[i] + beta[i - 1] * a[i - 1]) / (c[i] - alpha[i - 1] * a[i - 1]);
        }
    }

    private void calcXiAndEta() {

        int len = size - mid - 1;
        int count = size - 1;

        for (int i = 1; i <= mid; ++i) {

            xi[len - i] = a[count - i - 1] / (c[count - i] - b[count - i] * xi[len - i + 1]);
            eta[len - i] = (f[count - i] + b[count - i] * eta[len - i + 1]) /
                    (c[count - i] - b[count - i] * xi[len - i + 1]);
        }
    }

    private void calcDet() {

        det = c[0];
        int count = 1;

        for (int i = 1; i < size - mid; ++i) {

            det *= c[i] - alpha[i - 1] * a[i - 1];
        }

        for (int i = size - mid; i < size; ++i) {

            det *= c[i] - xi[count] * b[i - 1];
            count++;
        }
    }

    public void showSolutions() {

        System.out.println("Solutions: \n");
        Vectors.show(solutions);
    }

    private void discrepancy() throws MyException {

        discrepancy = Vectors.minus(Matrices.multipleWithVector(originMatrix, solutions), f);
    }

    public void showDiscrepancy() throws MyException {

        discrepancy();
        System.out.println("Discrepancy vector: \n");
        Vectors.showExp(discrepancy);
    }

    public void showDet() {

        calcDet();
        System.out.println("Determinant:\n");
        System.out.printf("%.5f\n\n", det);
    }
}
