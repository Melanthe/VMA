package RootMethod;

import common.MyException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {

            Root root = new Root();

            root.input();
            root.showOriginalMatrix();

            root.rootMethod();

            root.showSolutions();
            root.showDiscrepancy();
            root.showDet();

            root.showRate();

        } catch (MyException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.printf("Input/output error!");
        }
    }
}
