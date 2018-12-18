package Test;

import common.Matrices;
import common.MyException;
import common.Vectors;

public class Test {
    public static void main(String[] args) {

        double[] vector1 = {49, -7, 1};
        double[] vector2 = {};
        double[][] matrix1 = {{0.027, 0.222, -0.8055}, {0.0694, 0.055, -2.0138}, {0, 0, 1}};
        double[][] matrix2 = {{}, {}, {}};

        try {

            Vectors.show(Matrices.multipleWithVector(matrix1, vector1));

        } catch (MyException e) {
            System.out.println(e);
        }
    }
}
