package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Zakharov extends Function {
    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        double firstSum = 0.0;
        double secondSum = 0.0;

        double result = 0.0;

        double[] factors = new double[point.getDimensions()];
        for (int i = 0; i < point.getDimensions(); i++) {
            factors[i] = 0.5*(i+1);
        }

        for (int i = 0; i < point.getDimensions(); i++) {
            firstSum += Math.pow(X[i], 2);
            secondSum += factors[i]*X[i];
        }

        result = firstSum + Math.pow(secondSum, 2) + Math.pow(secondSum, 4);

        return result;
    }

    @Override
    public Vector gradient(Point point) {
        double[] X = point.getLocation();
        double[] result = new double[point.getDimensions()];

        double firstSum = 0.0;
        double secondSum = 0.0;

        double[] factors = new double[point.getDimensions()];
        for (int i = 0; i < point.getDimensions(); i++) {
            factors[i] = 0.5*(i+1);
        }

        for (int i = 0; i < point.getDimensions(); i++) {
            firstSum += Math.pow(X[i], 2);
            secondSum += factors[i]*X[i];
        }

        for (int i = 0; i < point.getDimensions(); i++) {
            result[i] = 2*X[i] + factors[i]*2*secondSum + factors[i]*4*Math.pow(secondSum, 3);
        }

        return new Vector(result);
    }

    @Override
    public String toString(){
        return "Zakharov";
    }
}
