package SimpleIterationMethod;

import common.MyException;
import java.io.IOException;

public class SimpleIterationMethod {
    public static void main(String[] args) {

        try {

            SimpleIteration si = new SimpleIteration();

            si.input();
            si.method();
            si.showOriginalMatrix();
            si.showSolutions();
            si.showDiscrepancy();
            si.showPriorIterations();
            si.showIterations();
            si.showRate();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }
    }
}
