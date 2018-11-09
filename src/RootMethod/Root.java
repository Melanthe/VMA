package RootMethod;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import common.Matrices;
import common.MyException;
import common.Vectors;

public class Root {

    private int size;
    private double[][] originMatrix;
    private double[] freeTerms;

    private double[][] s;
    private double[] y;

    private double[] solutions;

    private double[] discrepancy;

    private double det;

    public Root(int size) {

        this.size = size;
        init(size);
        det = 1;
    }

    private void init(int size) {

        originMatrix = new double[size][size];
        s = new double[size][size];
        y = new double[size];
        freeTerms = new double[size];
        solutions = new double[size];
        discrepancy = new double[size];
    }

    public Root() {

        this(0);
    }

    public void input() throws IOException, MyException {

        try (Scanner sc = new Scanner(new File("src/matrix.txt"))) {

            size = Integer.parseInt(sc.next());
            init(size);

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    originMatrix[i][j] = Double.parseDouble(sc.next());
                }
                freeTerms[i] = Double.parseDouble(sc.next());
            }
        }

        changeOriginal();
    }

    private void changeOriginal() throws MyException {

        double[][] tmp;
        tmp = Matrices.transposition(originMatrix);

        originMatrix = Matrices.multiple(tmp, originMatrix);
        freeTerms = Matrices.multipleWithVector(tmp, freeTerms);
    }

    public void rootMethod() {

        findS();
        findY();
        findSolution();
    }

    private void findS() {

        double tmpSum = 0.0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {

                if(i <= j) {

                    for (int k = 0; k <= (i - 1); ++k) {
                        tmpSum += s[k][i] * s[k][j];
                    }

                    if (i == j) {

                        s[i][j] = Math.sqrt(Math.abs(originMatrix[i][j] - tmpSum));

                    } else {

                        s[i][j] = (originMatrix[i][j] - tmpSum) / s[i][i];
                    }
                }

                tmpSum = 0.0;
            }
        }
    }

    private void findY() {

        double tmpSum = 0.0;

        for (int i = 0; i < size; ++i) {

            for (int k = 0; k <= (i - 1); ++k) {
                tmpSum += s[k][i] * y[k];
            }
            y[i] = (freeTerms[i] - tmpSum) / s[i][i];

            tmpSum = 0.0;
        }
    }

    private void findSolution() {

        double tmpSum = 0.0;

        for (int i = (size - 1); i >= 0; --i) {

            for (int k = (i + 1); k < size; ++k) {
                tmpSum += s[i][k] * solutions[k];
            }

            solutions[i] = (y[i] - tmpSum) / s[i][i];

            tmpSum = 0.0;
        }
    }

    private void discrepancy() throws MyException {

        discrepancy = Vectors.minus(Matrices.multipleWithVector(originMatrix, solutions), freeTerms);
    }

    private void findDet() {

        for (int i = 0; i < size; ++i) {
             det *= s[i][i];
        }


    }

    public void showOriginalMatrix() {

        System.out.println("Original matrix: \n");
        Matrices.showFull(originMatrix, freeTerms);
    }

    public void showSolutions() {

        System.out.println("Solutions: \n");
        Vectors.show(solutions);
    }

    public void showDiscrepancy() throws MyException {

        discrepancy();
        System.out.println("Discrepancy vector: \n");
        Vectors.showExp(discrepancy);
    }

    public void showDet() {

        findDet();

        System.out.println("Determinant:\n");
        System.out.printf("%.5f\n\n", det);
    }

    public void showRate() {

        System.out.println("Rate of the discrepancy vector: \n");
        System.out.printf("%.5e", Vectors.cubicRate(discrepancy));
    }
}
