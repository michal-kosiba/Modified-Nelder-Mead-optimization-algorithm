package optimization.main;

import optimization.algorithms.ModifiedSimplex;
import optimization.benchmarkFunctions.*;
import optimization.utilities.*;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {

        int DIMENSIONS = 3;
        double MIN_BORDER = -5*DIMENSIONS;
        double MAX_BORDER = 5*DIMENSIONS;

        Function sphere = new Sphere();
        Function rosenbrock = new Rosenbrock(1, 2.5);
        Function zakharov = new Zakharov();
        Function trid = new Trid();

        Function f = trid;

        double[] borders = new double[2*DIMENSIONS];
        for (int i = 0; i < 2*DIMENSIONS; i+=2) {
            borders[i] = MIN_BORDER;
            borders[i+1] = MAX_BORDER;
        }

        RandomPointFactory rpf = new RandomPointFactory(DIMENSIONS, borders);
        Point[] start = rpf.randomPoints(100);
        Point result;

        OptWriter swResult= new OptWriter("results\\Modified Simplex results for " + f.toString() + DIMENSIONS + "D" , new DecimalFormat("0.0000000000000000"));
        swResult.initialize("Zeta", "Value", "Function calls");

        OptWriter swStartPoints= new OptWriter("startPoints\\Modified Simplex starting points for " + f.toString() + DIMENSIONS + "D" , new DecimalFormat("0.0000000000000000"));
        OptWriter swEndPoints= new OptWriter("endPoints\\Modified Simplex end points  for " + f.toString() + DIMENSIONS + "D" , new DecimalFormat("0.0000000000000000"));
        String[] points = new String[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            points[i] = "X" + i;
        }
        swStartPoints.initialize(points);
        swEndPoints.initialize(points);

        ModifiedSimplex ms;
        for(int i=0; i<=10; i++){
            for (int j = 0; j < 100; j++) {

                ms  = new ModifiedSimplex(DIMENSIONS, 1, 2, 0.5, 0.5, i*0.1, 1e-8, 10000);
                result = ms.run(start[j], f);

                swStartPoints.append(start[j]);

                swResult.append(i*0.1);
                swResult.append(f.call(result));
                swResult.append(ms.getFunctionCalls());

                swEndPoints.append(result);

                swResult.push();
                swStartPoints.push();
                swEndPoints.push();
            }
        }
        swResult.save();
        swStartPoints.save();
        swEndPoints.save();
    }
}
