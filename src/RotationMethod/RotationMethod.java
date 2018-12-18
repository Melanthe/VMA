package RotationMethod;

import common.MyException;
import java.io.IOException;

public class RotationMethod {
    public static void main(String[] args) {

        try {
            Rotation rt = new Rotation();
            rt.input();
            rt.showOriginMatrix();

            rt.method();
            rt.showResultMatrix();
            rt.showEigenVectors();

        } catch (IOException e) {
            System.out.println("Input error!");
        } catch (MyException e) {
            System.out.println(e);
        }
    }
}
