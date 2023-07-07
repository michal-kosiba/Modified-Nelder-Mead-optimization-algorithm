package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class SteepestDescent extends Algorithm{

    double h0 = 1;
    double epsilon = 0.001;
    int maxIterations = 1000;
    int iterations = 0;

    @Override
    public Point run(Point startingPoint, Function f) {
        Point X0 = startingPoint.copy();
        Point X1;
        Vector g;
        Vector d;
        while(true){
            g = f.gradient(X0);
            d = g.reverse().multiply(h0);
            X1 = X0.copy().shift(d);
            System.out.println("-----\nX = " + X0.toString() + "   Gradient: " + g.toString() + "\n-----");
            if(new Vector(X0,X1).getLength() < epsilon || iterations >= maxIterations){
                return X1;
            }
            X0 = X1;
        }
    }
}
