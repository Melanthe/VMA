package ReflectionMethod;

import common.Matrices;
import common.MyException;
import common.Vectors;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Reflection {

    private int size;
    private double[][] originMatrix;
    private double[] freeTerms;

    private double[][] resultMatrix;
    private double[] resultFreeTerms;

    private double[] solutions;
    private double[] discrepancy;

    private double det;

    public Reflection(int size) {

        init(size);
        det = 1;
    }

    private void init(int size) {

        this.size = size;

        originMatrix = new double[size][size];
        freeTerms = new double[size];
        solutions = new double[size];
        discrepancy = new double[size];
        resultMatrix = new double[size][size];
        resultFreeTerms = new
                double[size];
    }

    public Reflection() {

        this(0);
    }

    public void input() throws IOException{

        try (Scanner sc = new Scanner(new File("src/matrix.txt"))) {

            size = Integer.parseInt(sc.next());
            init(size);

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    originMatrix[i][j] = Double.parseDouble(sc.next());
                    resultMatrix[i][j] = originMatrix[i][j];

                }
                freeTerms[i] = Double.parseDouble(sc.next());
                resultFreeTerms[i] = freeTerms[i];
            }
        }
    }

    public void method() throws MyException {

        double[][] transp;
        double[][] unit = Matrices.getUnitMatrix(size);
        double[] omega;
        double alpha;
        double chi;
        double scalarProduct;
        double[] vectorS;

        for (int k = 0; k < size - 1; ++k) {

            transp = Matrices.transposition(resultMatrix);
            vectorS = transp[k].clone();

            for (int i = 0; i < k; ++i) { 
                vectorS[i] = 0.0;
            }

            alpha = Math.sqrt(Vectors.scalarProduct(vectorS, vectorS));
            chi = 1 / (Math.sqrt(2 * (Vectors.scalarProduct(vectorS, Vectors.minus(vectorS,
                    Vectors.multipleWithScalar(unit[k], alpha))))));
            omega = Vectors.multipleWithScalar(Vectors.minus(vectorS,
                    Vectors.multipleWithScalar(unit[k], alpha)), chi);

            scalarProduct = Vectors.scalarProduct(resultFreeTerms, omega);
            
            for (int i = k; i < size; ++i) {
                for (int j = k; j < size; ++j) {

                    resultMatrix[i][j] = resultMatrix[i][j] - 2 * (Vectors.scalarProduct(transp[j], omega)) * omega[i];
                }

                resultFreeTerms[i] = resultFreeTerms[i] - 2 * scalarProduct * omega[i];
            }

            resultMatrix[k][k] = alpha;

            det *= alpha;
        }

        det *= resultMatrix[size - 1][size - 1];

        findSolution();
    }

    private void findSolution() {

        double tmpSum = 0.0;

        for (int i = (size - 1); i >= 0; --i) {

            for (int k = (i + 1); k < size; ++k) {
                tmpSum += resultMatrix[i][k] * solutions[k];
            }

            solutions[i] = (resultFreeTerms[i] - tmpSum) / resultMatrix[i][i];

            tmpSum = 0.0;
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

    public void showDet() {

        System.out.println("Determinant:\n");
        System.out.printf("%.5f\n\n", det);
    }

    public void showRate() {

        System.out.println("Rate of the discrepancy vector: \n");
        System.out.printf("%.5e\n\n", Vectors.cubicRate(discrepancy));
    }
}
