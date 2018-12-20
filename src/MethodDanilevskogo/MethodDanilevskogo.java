    package MethodDanilevskogo;

    import common.MyException;

    import java.io.IOException;

    public class MethodDanilevskogo {
        public static void main(String[] args) {

            try {

                Danilevskogo dv = new Danilevskogo();
                dv.input();
                dv.makeFrobeniusMatrix();
                dv.getEigenVectors();

                dv.showOriginalMatrix();
                dv.showFrobeniusMatrix();
                dv.showEigenVectors();
                dv.showDiscrepancy();

            } catch (IOException e) {
                System.out.println("Input error!");
            } catch (MyException e) {
                System.out.println(e);
            }
        }
    }
