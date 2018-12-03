package GradientDescentMethod;

import common.Matrices;
import common.MyException;
import common.Vectors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GradientDescent {

    private int size;
    private double[][] originMatrix;
    private double[] freeTerms;

    private final double EPSILON;
    private double[] vectorG;

    private double[][] resultMatrix;
    private double[] resultFreeTerms;

    private double[] solutions;
    private double[] discrepancy;

    private int iterations;

    public GradientDescent(int size) {

        init(size);
        iterations = 0;
        EPSILON = Math.pow(10, -5);
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        freeTerms = new double[size];
        solutions = new double[size];
        vectorG = new double[size];
        discrepancy = new double[size];
        resultMatrix = new double[size][size];
        resultFreeTerms = new double[size];
    }

    public GradientDescent() {

        this(0);
    }

    public void input() throws IOException {

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
    }

    private void initMethod() throws MyException {

        double[][] tmp = Matrices.transposition(originMatrix);
        resultMatrix = Matrices.multiple(tmp, originMatrix);
        resultFreeTerms = Matrices.multipleWithVector(tmp, freeTerms);

        for(int i = 0; i < size; ++i) {

            vectorG[i] = resultFreeTerms[i] / resultMatrix[i][i];
        }
    }

    public void method() throws MyException {

        initMethod();

        double[] curX = new double[size];
        double[] lastX = vectorG.clone();
        double[] tmpDiscrepancy;
        double scalarProduct;
        double tmpRate;
        boolean flag = true;

        while (flag) {

            iterations++;

            tmpDiscrepancy = Vectors.minus(Matrices.multipleWithVector(resultMatrix, lastX), resultFreeTerms);
            scalarProduct = Vectors.scalarProduct(tmpDiscrepancy, tmpDiscrepancy) /
                    Vectors.scalarProduct(Matrices.multipleWithVector(resultMatrix, tmpDiscrepancy), tmpDiscrepancy);

            curX = Vectors.minus(lastX, Vectors.multipleWithScalar(tmpDiscrepancy, scalarProduct));

            tmpRate = Vectors.cubicRate(Vectors.minus(curX, lastX));
            if (tmpRate < EPSILON) {
                flag = false;
            }

            lastX = curX.clone();
        }

        solutions = curX.clone();
    }

    public void showOriginalMatrix() {

        System.out.println("Original matrix: \n");
        Matrices.showFull(originMatrix, freeTerms);
    }

    public void showSolutions() {

        System.out.println("Solutions: \n");
        Vectors.show(solutions);
    }

    private void discrepancy() throws MyException {

        discrepancy = Vectors.minus(Matrices.multipleWithVector(originMatrix, solutions), freeTerms);
    }

    public void showDiscrepancy() throws MyException {

        discrepancy();
        System.out.println("Discrepancy vector: \n");
        Vectors.showExp(discrepancy);
    }

    public void showIterations() {

        System.out.println("The number of iterations: \n");
        System.out.printf("%d\n\n", iterations);
    }

    public void showRate() {

        System.out.println("Rate of the discrepancy vector: \n");
        System.out.printf("%.5e\n\n", Vectors.cubicRate(discrepancy));
    }
}