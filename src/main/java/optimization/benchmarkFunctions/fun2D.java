package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class fun2D extends Function {

    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        return X[0] * 3 + X[1] * 7 + 5;
    }

    @Override
    public Vector gradient(Point point) {
        return new Vector(3.0, 7.0);
    }
}
