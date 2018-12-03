package GradientDescentMethod;

import common.MyException;
import java.io.IOException;

public class GradientDescentMethod {
    public static void main(String[] args) {

        try {

            GradientDescent gd = new GradientDescent();
            gd.input();
            gd.method();
            gd.showOriginalMatrix();
            gd.showSolutions();
            gd.showDiscrepancy();
            gd.showIterations();
            gd.showRate();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }

    }
}
