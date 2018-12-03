package RelaxationMethod;

import common.MyException;
import java.io.IOException;

public class RelaxationMethod {
    public static void main(String[] args) {

        try {

            Relaxation rl = new Relaxation();
            rl.input();
            rl.method();
            rl.showOriginalMatrix();
            rl.showSolutions();
            rl.showDiscrepancy();
            rl.showIterations();
            rl.showRate();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }
    }
}
