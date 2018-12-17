package MethodKrylova;

import common.MyException;
import java.io.IOException;

public class MethodKrylova {
    public static void main(String[] args) {

        try {

            Krylov kv = new Krylov();
            kv.input();
            kv.makeEigenPolynomial();
            kv.getEigenVectors();

            kv.showChangedMatrix();
            kv.showMatrixC();
            kv.showEigenVectors();
            kv.showDiscrepancy();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }

    }
}
