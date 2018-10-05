import java.util.InputMismatchException;
import java.util.Scanner;

public class Gauss {
    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter a size of the matrix: ");

            while(!sc.hasNextInt()) {
                System.out.println("Incorrect input!");
                sc.next();
            }
            int size = sc.nextInt();

            Matrix matrix = new Matrix(size);
            matrix.input();

            matrix.showOriginal();

            matrix.forwardElimination();
            matrix.showResultMatrix();

            matrix.backElimination();
            matrix.showSolution();

            matrix.showDet();
            matrix.discrepancyVector();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
