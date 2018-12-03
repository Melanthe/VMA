package GaussSeidelMethod;

import common.MyException;
import java.io.IOException;

public class GaussSeidelMethod {
    public static void main(String[] args) {

        try {

            GaussSeidel gs = new GaussSeidel();
            gs.input();
            gs.method();
            gs.showOriginalMatrix();
            gs.showSolutions();
            gs.showDiscrepancy();
            gs.showIterations();
            gs.showRate();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }
    }
}
