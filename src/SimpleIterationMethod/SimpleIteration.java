package SimpleIterationMethod;

import common.Matrices;
import common.MyException;
import common.Vectors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SimpleIteration {

    private int size;
    private double[][] originMatrix;
    private double[] freeTerms;

    private double[][] matrixB;
    private double[] vectorG;
    private final double EPSILON;

    private double[] solutions;
    private double[] discrepancy;

    private int iterations;

    public SimpleIteration(int size) {

        init(size);
        iterations = 0;
        EPSILON = Math.pow(10, -5);
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        freeTerms = new double[size];
        solutions = new double[size];
        discrepancy = new double[size];
        matrixB = new double[size][size];
        vectorG = new double[size];
    }

    public SimpleIteration() {

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

        double[][] unit = Matrices.getUnitMatrix(size);
        double[][] transp = Matrices.transposition(originMatrix);
        double[][] tmpMatrix = Matrices.multiple(transp, originMatrix);
        double tmpRate = Matrices.cubicRate(tmpMatrix);

        matrixB = Matrices.minus(unit, Matrices.divideWithScalar(tmpMatrix, tmpRate));
        vectorG = Vectors.divideWithScalar(Matrices.multipleWithVector(transp, freeTerms), tmpRate);
    }

    public void method() throws MyException {

        initMethod();
        checkConvergence();

        double[] curX = new double[size];
        double[] lastX = vectorG.clone();
        double tmpRate;
        boolean flag = true;

        while (flag) {

            iterations++;

            curX = Vectors.plus(Matrices.multipleWithVector(matrixB, lastX), vectorG);

            tmpRate = Vectors.cubicRate(Vectors.minus(curX, lastX));
            if (tmpRate < EPSILON) {
                flag = false;
            }

            lastX = curX.clone();
        }

        solutions = curX.clone();
    }

    private void checkConvergence() {

        double rate = Matrices.cubicRate(matrixB);

        if (rate < 1) {
            System.out.println("\nMethod converges!\nRate of the matrix B is: " + rate + "\n\n");
        } else {
            System.out.println("\nCan't say nothing about convergence of the method!\n\n");
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

    private void discrepancy() throws MyException {

        discrepancy = Vectors.minus(Matrices.multipleWithVector(originMatrix, solutions), freeTerms);
    }

    public void showDiscrepancy() throws MyException {

        discrepancy();
        System.out.println("Discrepancy vector: \n");
        Vectors.showExp(discrepancy);
    }

    public void showPriorIterations() {

        double matrixRate = Matrices.cubicRate(matrixB);
        double vectorRate = Vectors.cubicRate(vectorG);

        double priorIt = (Math.log(EPSILON * (1.0 - matrixRate) / vectorRate) / Math.log(matrixRate)) - 1.0;
        System.out.println("The number of prior iterations: \n");
        System.out.printf("%.3f\n\n", priorIt);
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
