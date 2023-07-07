package optimization.benchmarkFunctions;
import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Linear extends Function {

    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        return X[0]*3 + 1;
    }

    @Override
    public Vector gradient(Point point) {
        return new Vector(3.0);
    }
}
