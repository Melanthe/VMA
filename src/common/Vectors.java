package common;

public class Vectors {

    public static double[] minus(double[] x, double[] y) throws MyException {

        if (x.length == y.length) {

            double[] res = new double[x.length];

            for (int i = 0; i < x.length; ++i) {

                res[i] = x[i] - y[i];
            }

            return res;

        } else {
            throw new MyException("Incorrect vectors for minus!");
        }
    }

    public static double[] plus(double[] x, double[] y) throws MyException {

        if (x.length == y.length) {

            double[] res = new double[x.length];

            for (int i = 0; i < x.length; ++i) {

                res[i] = x[i] + y[i];
            }

            return res;

        } else {
            throw new MyException("Incorrect vectors for minus!");
        }
    }

    public static double[] multipleWithScalar(double[] vector, double scalar) {

        double[] res = new double[vector.length];

        for (int i = 0; i < vector.length; ++i) {

            res[i] = vector[i] * scalar;
        }

        return res;
    }

    public static double[] divideWithScalar(double[] vector, double scalar) throws MyException {

        if (scalar == 0) {
            throw new MyException("Divide by 0!");
        }

        double[] res = new double[vector.length];

        for (int i = 0; i < vector.length; ++i) {

            res[i] = vector[i] / scalar;
        }

        return res;
    }

    public static double[][] multipleColumnWithRow(double[] column, double[] row) throws MyException {

            double[][] res = new double[column.length][row.length];

            for (int i = 0; i < column.length; ++i) {
                for (int j = 0; j < row.length; ++j) {

                    res[i][j] += column[i] * row[j];
                }
            }

            return res;
    }

    public static double scalarProduct(double[] x, double[] y) throws MyException {

        if (x.length == y.length) {

            double res = 0.0;

            for (int i = 0; i < x.length; ++i) {

                res += x[i] * y[i];
            }

            return res;

        } else {
            throw new MyException("Incorrect vectors for scalar product!");
        }
    }

    public static double cubicRate(double[] vector) {

        double rate = Math.abs(vector[0]);

        for (int i = 0; i < vector.length; ++i) {
            if (Math.abs(vector[i]) > rate) {
                rate = Math.abs(vector[i]);
            }
        }

        return rate;
    }

    public static void show(double[] vector) {

        for (double item : vector) {
            System.out.printf("%8.5f ", item);
        }

        System.out.println("\n");
    }

    public static void showExp(double[] vector) {

        for (double item : vector) {
            System.out.printf("%8.5e ", item);
        }

        System.out.println("\n");
    }
}
