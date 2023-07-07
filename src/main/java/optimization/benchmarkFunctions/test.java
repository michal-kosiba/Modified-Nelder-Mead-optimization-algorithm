package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class test extends Function {
    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        return Math.pow(X[0] - 3, 2) + Math.pow(X[1] + 2, 2) + 2;
    }

    @Override
    public Vector gradient(Point point) {
        double[] X = point.getLocation();
        return new Vector(2*X[0] - 6, 2*X[1] + 4);
    }
}
