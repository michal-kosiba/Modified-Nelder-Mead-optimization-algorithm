package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class StyblinskiTang extends Function {
    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        double result = 0.0;
        for (int i = 0; i < X.length; i++) {
            result += 0.5*(Math.pow(X[i], 4) - 16*Math.pow(X[i], 2) + 5*X[i]);
        }
        return result;
    }

    @Override
    public Vector gradient(Point point) {
        double[] X = point.getLocation();
        double[] gradient = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            gradient[i] = 0.5*(4*Math.pow(X[i], 3) - 32*X[i] + 5);
        }
        return new Vector(gradient);
    }
}
