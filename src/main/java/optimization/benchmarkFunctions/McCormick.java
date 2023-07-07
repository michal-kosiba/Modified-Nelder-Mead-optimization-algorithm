package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class McCormick extends Function {
    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        return Math.sin(X[0] + X[1]) + Math.pow(X[0] - X[1], 2) - 1.5*X[0] + 2.5*X[1] + 1;
    }

    @Override
    public Vector gradient(Point point) {
        double[] X = point.getLocation();
        return new Vector(
                Math.cos(X[0]+X[1]) + 2*(X[0] - X[1]) - 1.5,
                Math.cos(X[0]+X[1]) - 2*(X[0] - X[1]) + 2.5);
    }
}
