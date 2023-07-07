package optimization.main;

import optimization.algorithms.*;
import optimization.benchmarkFunctions.Sphere;
import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.RandomPointFactory;

public class SecondMain {
    public static void main(String[] args) {
//        Rosenbrock rosenbrock = new Rosenbrock(1, 2, 0.5, 1e-3, 1000);
//        Simplex simplex = new Simplex(1, 1, 2, 0.5, 0.5, 1e-3, 1000);
//        ConjugateGradient gradient = new ConjugateGradient(0.1,1e-3, 1000);
//        HookeJeeves hookeJeeves = new HookeJeeves(2, 1, 1e-3, 1000);
//
//        Function sphere = new Sphere();
//        RandomPointFactory rpf = new RandomPointFactory(2, -10, 10, -10, 10);
//        Point[] points = rpf.randomPoints(100);
//        int numberForRosen = 0;
//        int numberForSimplex = 0;
//        int numberForGradient = 0;
//        int numberForHookeJeves = 0;
//
//        for (int i = 0; i < 100; i++) {
//            simplex.run(points[i], sphere);
//            numberForSimplex += simplex.getFunctionCalls();
//
//            rosenbrock.run(points[i], sphere);
//            numberForRosen += rosenbrock.getFunctionCalls();
//
//            gradient.run(points[i], sphere);
//            numberForGradient += gradient.getFunctionCalls();
//
//            hookeJeeves.run(points[i], sphere);
//            numberForHookeJeves += hookeJeeves.getFunctionCalls();
//        }
//
//        System.out.println("Hooke Jeves: " + numberForHookeJeves/100);
//        System.out.println("Simplex: " + numberForSimplex/100);
//        System.out.println("Rosenbrock: " + numberForRosen/100);
//        System.out.println("Gradient: " + numberForGradient/100);
        System.out.println(100*(int)(Math.pow(10, Math.log(16)/Math.log(2))));
    }
}
