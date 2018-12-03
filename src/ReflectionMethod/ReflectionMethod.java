package ReflectionMethod;

import common.MyException;
import java.io.IOException;

public class ReflectionMethod {
    public static void main(String[] args) {

        try {

            Reflection rf = new Reflection();
            rf.input();
            rf.method();
            rf.showOriginalMatrix();
            rf.showChangedMatrix();
            rf.showSolutions();
            rf.showDiscrepancy();
            rf.showRate();
            rf.showDet();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }

    }
}
