package TridiagonalMethod;

import common.MyException;

import java.io.IOException;

public class TridiagonalMethod {
    public static void main(String[] args) {

        try {

            Tridiag tridiag = new Tridiag();
            tridiag.input();
            tridiag.TridiagonalMethod();
            tridiag.showOriginalMatrix();
            tridiag.showCoefficient();
            tridiag.showSolutions();
            tridiag.showDiscrepancy();
            tridiag.showDet();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }
    }
}
