package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Matyas extends Function {
    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        return 0.26*(Math.pow(X[0], 2) + Math.pow(X[1], 2)) - 0.48*X[0]*X[1];
    }

    @Override
    public Vector gradient(Point point) {
        double[] X = point.getLocation();
        return new Vector(
                0.52 * X[0] - 0.48 * X[1],
                0.52 * X[1] - 0.48 * X[0]
        );
    }
}
