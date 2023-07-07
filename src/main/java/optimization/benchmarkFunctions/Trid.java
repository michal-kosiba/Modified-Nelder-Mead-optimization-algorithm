package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Trid extends Function {
    @Override
    public double call(Point point) {
        double[] X = point.getLocation();
        double result = 0.0;
        for (int i = 0; i < point.getDimensions(); i++) {
            result += Math.pow(X[i] - 1, 2);
        }
        for (int i = 1; i < point.getDimensions(); i++) {
            result -= X[i] * X[i - 1];
        }
        return result;
    }

    @Override
    public Vector gradient(Point point) {
        double[] X = point.getLocation();
        double[] result = new double[point.getDimensions()];
        for (int i = 0; i < point.getDimensions(); i++) {
            result[i] += 2 * X[i] - 2;
        }
        for (int i = 0; i < point.getDimensions(); i++) {
            if(i == 0){
                result[i] -= X[i + 1];
            } else if (i == point.getDimensions() - 1) {
                result[i] -= X[i - 1];
            } else{
                result[i] -= X[i + 1];
                result[i] -= X[i - 1];
            }
        }
        return new Vector(result);
    }

    @Override
    public String toString(){
        return "Trid";
    }
}
