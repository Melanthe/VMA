package common;

public class Vectors {

    public static double[] minus(double[] x, double[] y) throws MyException {

        if (x.length == y.length) {

            double[] res = new double[x.length];

            for (int i = 0; i < x.length; i++) {

                res[i] = x[i] - y[i];
            }

            return res;

        } else {
            throw new MyException("Incorrect vector for minus!");
        }
    }

    public static double rate(double[] vector) {

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
