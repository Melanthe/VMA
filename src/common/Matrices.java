package common;

public class Matrices {

    public static double[][] transposition(double[][] matrix) {

        int rows = matrix.length;
        int column = matrix[0].length;

        double[][] result = new double[column][rows];

        for (int i = 0; i < column; ++i) {
            for (int j = 0; j < rows; ++j) {

                result[i][j] = matrix[j][i];
            }
        }

        return result;
    }

    public static double[][] multiple(double[][] x, double[][] y) throws MyException {

      if (x[0].length == y.length) {

            double[][] res = new double[x.length][y[0].length];

            for (int i = 0; i < x.length; ++i) {
                for (int j = 0; j < y[0].length; ++j) {
                    for (int k = 0; k < y.length; ++k) {
                        res[i][j] += x[i][k] * y[k][j];
                    }
                }
            }

            return res;

        } else {
            throw new MyException("Incorrect matrix for multiplication!");
        }
    }

    public static double[][] multipleWithScalar(double[][] matrix, double scalar) {

        double[][] res = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {

                res[i][j] = matrix[i][j] * scalar;
            }
        }

        return res;
    }

    public static double[][] divideWithScalar(double[][] matrix, double scalar) throws MyException {

        if (scalar == 0) {
            throw new MyException("Divide by 0!");
        }

        double[][] res = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {

                res[i][j] = matrix[i][j] / scalar;
            }
        }

        return res;
    }

    public static double[] multipleWithVector(double[][] x, double[] y) throws MyException {

        if (x[0].length == y.length) {

            double[] res = new double[x.length];

            for (int i = 0; i < x.length; ++i) {
                for (int j = 0; j < x[0].length; ++j) {
                    res[i] += x[i][j] * y[j];
                }
            }

            return res;

        } else {
            throw new MyException("Incorrect matrix for multiplication!");
        }
    }

    public static double[][] minus(double[][] x, double[][] y) throws MyException {

        if ((x.length == y.length) && (x[0].length == y[0].length)) {

            double[][] res = new double[x.length][x.length];

            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x.length; j++) {

                    res[i][j] = x[i][j] - y[i][j];
                }
            }

            return res;

        } else {
            throw new MyException("Incorrect vector for minus!");
        }
    }

    public static double[][] getUnitMatrix(int size) {

        double[][] res = new double[size][size];

        for (int i = 0; i < size; ++i) {

            res[i][i] = 1;
        }

        return res;
    }

    public static void show(double[][] matrix) {

        for (double[] row : matrix) {
            for (double item : row) {

                System.out.printf("%10.5f ", item);
            }

            System.out.println();
        }

        System.out.println();
    }

    public static void showFull(double[][] matrix, double[] vector) {

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {

                System.out.printf("%10.5f ", matrix[i][j]);
            }

            System.out.printf(" | %8.5f\n", vector[i]);
        }

        System.out.println();
    }

    public static void showExp(double[][] matrix) {

        for (double[] row : matrix) {
            for (double item : row) {

                System.out.printf("%12.5e ", item);
            }

            System.out.println();
        }

        System.out.println();
    }

    public static double cubicRate(double[][] matrix) {

        double max = Double.MIN_VALUE;
        double sum = 0.0;

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {

                sum += Math.abs(matrix[i][j]);
            }

            if (sum > max) {
                max = sum;
            }

            sum = 0.0;
        }

        return max;
    }
}