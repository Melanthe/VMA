import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Matrix {

    private int size;
    private double[][] originMatrix;
    private double[] freeTerms;

    private double[][] resultMatrix;
    private double[] resultFreeTerms;
    private double[] solutions;

    private boolean sign;


    public Matrix(int size) {

        this.size = size;
        originMatrix = new double[size][size];
        freeTerms = new double[size];
        resultMatrix = new double[size][size];
        resultFreeTerms = new double[size];
        solutions = new double[size];
        sign = false;
    }

    public Matrix() {

        this(0);
    }

    public void input() throws InputMismatchException {

        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);

        System.out.println("Input elements of the original matrix: ");

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {

                originMatrix[i][j] = sc.nextDouble();
                resultMatrix[i][j] = originMatrix[i][j];
            }
        }

        System.out.println("Input free terms: ");

        for (int i = 0; i < size; ++i) {

            freeTerms[i] = sc.nextDouble();
            resultFreeTerms[i] = freeTerms[i];
        }
    }

    public void showOrigin() {

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {

                System.out.print(originMatrix[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();
    }

    public void forwardElimination() {

        int maxLine = findMax(0);

    }

    private int findMax(int step) {

        double max = resultMatrix[step][step];
        int line = step;

        for (int i = step; i < size; ++i) {
            if (resultMatrix[i][step] > max) {
                max = resultMatrix[i][step];
                line = i;
            }
        }

        return line;
    }
}
