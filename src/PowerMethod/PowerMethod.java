package PowerMethod;

import common.MyException;

import java.io.IOException;

public class PowerMethod {
    public static void main(String[] args) {

        try {
            Power pw = new Power();
            pw.input();
            pw.method();

            pw.showChangedMatrix();
            pw.showEigenValue();
            pw.showEigenVector();
            pw.showDiscrepancy();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }
    }
}
