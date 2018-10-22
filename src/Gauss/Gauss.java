package Gauss;

import java.io.IOException;
import java.util.InputMismatchException;

public class Gauss {
    public static void main(String[] args) {

        try {

            Matrix matrix = new Matrix();
            matrix.input();

            matrix.showOriginal();

            matrix.forwardElimination();
            matrix.showResultMatrix();

            matrix.backElimination();
            matrix.showSolution();

            matrix.showDet();
            matrix.showDiscrepancyVector();

            matrix.showInverseMatrix();
            matrix.showDiscrepancyMatrix();

            matrix.vectorRate();
            matrix.matrixRate();

        } catch (InputMismatchException e) {
            System.out.println("Incorrect type of data!");

        } catch (IOException e) {
            System.out.println("Incorrect input!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
