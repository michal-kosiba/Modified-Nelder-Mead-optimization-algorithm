package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Matrix;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class HookeJeeves extends Algorithm{

    private double alpha;
    private double s;
    private double epsilon;
    private int maxCalls;
    private int functionCalls;

    private double trialValue;


    public HookeJeeves(double alpha, double s0, double epsilon, int maxCalls){
        this.alpha = alpha;
        this.s = s0;
        this.epsilon = epsilon;
        this.maxCalls = maxCalls;
    }

    @Override
    public Point run(Point startingPoint, Function f) {
        functionCalls = 0;
        Point X = startingPoint.copy();
        double valueX;

        Point XB = X.copy();
        double valueXB = f.call(XB);
        functionCalls ++;

        Point previousXB;

        while (true){
            X = trial(XB, s, f);
            valueX = trialValue;
            System.out.println("X bazowe: " + XB);
            if(valueX < valueXB){
                while(true){
                    previousXB = XB;
                    XB = X;
                    valueXB = valueX;

                    Point temp = XB.toVector().multiply(2.0).toPoint();
                    X = XB.shift(new Vector(previousXB, temp));
                    X = trial(X, s, f);
                    valueX = trialValue;

                    if(valueX >= valueXB) {
                        if(functionCalls > maxCalls){
                            return XB;
                        }
                        break;
                    }
                }
            }
            else{
                s *= alpha;
            }

            if(s < epsilon || functionCalls > maxCalls){
                return XB;
            }
        }
    }

    private Point trial(Point X0, double s, Function f){
        int dimensions = X0.getDimensions();
        Matrix D = Matrix.identityMatrix(dimensions);
        Point X1;
        double value0 = f.call(X0);
        functionCalls ++;
        double value1;

        for (int i = 0; i < dimensions; i++) {
            X1 = X0.shift(D.getColumn(i).multiply(s));
            value1 = f.call(X1);
            functionCalls ++;

            if(value1 < value0){
                X0 = X1;
                value0 = value1;
            }
            else {
                X1 = X0.shift(D.getColumn(i).multiply(-s));
                value1 = f.call(X1);
                functionCalls ++;
                if(value1 < value0){
                    X0 = X1;
                    value0 = value1;
                }
            }
        }
        trialValue = value0;
        return X0;
    }

    public int getFunctionCalls(){
        return functionCalls;
    }
}
