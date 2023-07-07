package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Rosenbrock2D extends Function {
    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        return Math.pow(10*(X[1] - Math.pow(X[0], 2)), 2) + Math.pow((1 - X[0]), 2);
    }

    @Override
    public Vector gradient(Point point) {
        double[] X = point.getLocation();
        return new Vector(
                40*Math.pow(X[0],3) + 2*X[0] - 2 - 40*X[0]*X[1],
                20*X[1] - 20*Math.pow(X[0], 2)
        );
    }
}
