package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Sphere extends Function {

    @Override
    public double call(Point point) {
        double result = 0;
        for (int i = 0; i < point.getDimensions(); i++) {
            result += Math.pow(point.getLocation()[i], 2);
        }
        return result;
    }

    @Override
    public Vector gradient(Point point) {
        double[] result = new double[point.getDimensions()];
        for (int i = 0; i < point.getDimensions(); i++) {
            result[i] = 2*point.getLocation()[i];
        }
        return new Vector(result);
    }

    @Override
    public String toString(){
        return "Sphere";
    }
}
