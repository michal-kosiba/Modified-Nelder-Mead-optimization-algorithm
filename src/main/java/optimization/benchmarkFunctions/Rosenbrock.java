package optimization.benchmarkFunctions;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Rosenbrock extends Function{

        private double a;
        private double b;

        public Rosenbrock(double a, double b){
            this.a = a;
            this.b = b;
        }

        public Rosenbrock(){
            this(1, 2.5);
        }

        @Override
        public double call(Point point) {
            double[] X = point.getLocation();
            double result = 0.0;
            for (int i = 0; i < X.length - 1; i++) {
                result += b*Math.pow((X[i+1] - Math.pow(X[i], 2)), 2) + Math.pow((a - X[i]), 2);
            }
            return result;
        }

        @Override
        public Vector gradient(Point point) {
            double[] X = point.getLocation();
            int dim = point.getDimensions();
            double[] result = new double[dim];
            result[0] = 4*b*Math.pow(X[0],3) + 2*X[0] - 2 - 4*b*X[0]*X[1];
            result[dim-1] = 2*b*X[dim-1] - 2*b*Math.pow(X[dim-2], 2);
            for (int i = 1; i < point.getDimensions()-1; i++) {
                result[i] = 2*b*X[i] - 2*b*Math.pow(X[i-1], 2) + 4*b*Math.pow(X[i],3) + 2*X[i] - 2*a - 4*b*X[i]*X[i+1];
            }

            return new Vector(result);
        }

        @Override
        public String toString(){
            return "Rosenbrock";
        }
    }
