package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Matrix;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class Rosenbrock extends Algorithm{

    double s0;
    double epsilon;
    double alpha;
    double beta;
    int maxCalls;
    int functionCalls;

    public Rosenbrock(double s0, double alpha, double beta, double epsilon, int maxCalls) {
        this.s0 = s0;
        this.epsilon = epsilon;
        this.alpha = alpha;
        this.beta = beta;
        this.maxCalls = maxCalls;
    }

    @Override
    public Point run(Point startingPoint, Function f) {
        functionCalls = 0;

        Point X0 = startingPoint.copy();
        Point X1 = null;

        int dimensions = X0.getDimensions();
        int[] p = new int[dimensions];

        Vector lambda = new Vector(dimensions);
        Matrix D = Matrix.identityMatrix(dimensions);
        Vector s = new Vector(dimensions);

        for (int i = 0; i < dimensions; i++) {
            s.setComponent(i, s0);
        }

        double v0 = f.call(X0);
        functionCalls ++;
        double v1;
        Vector shift;

        //główna pętla algorytmu
        while (true){

            for (int i = 0; i < dimensions; i++) {
                shift = D.getColumn(i).multiply(s.getComponent(i));
                X1 = X0.shift(shift);
                v1 = f.call(X1);
                functionCalls ++;

                if(v1 < v0){
                    lambda.addToComponent(i, s.getComponent(i));
                    s.multiplyComponent(i, alpha);
                    X0 = X1;
                    v0 = v1;
                }
                else {
                    s.multiplyComponent(i, -beta);
                    p[i] += 1;
                }
            }

            boolean allFailed = true;

            for (int i = 0; i < dimensions; i++) {
                if(lambda.getComponent(i) == 0.0 || p[i] == 0){
                    allFailed = false;
                    break;
                }
            }

            //base change
            if(allFailed){

                Matrix Q = new Matrix(dimensions, dimensions);
                Vector v = new Vector(dimensions);

                for (int i = 0; i < dimensions; ++i) {
                    for (int j = 0; j <= i; ++j){
                        Q.setElement(i, j, lambda.getComponent(i));
                    }
                }

                Q = Matrix.multiply(D, Q);
                v = Q.getColumn(0).normalize();
                D.setColumn(0, v);

                Vector temp = new Vector(dimensions);
                for (int i = 1; i < dimensions; i++) {
                    for (int j = 0; j < i; j++) {
                        temp = temp.add(D.getColumn(j).multiply(Q.transpose().getColumn(i).dotProduct(D.getColumn(j))));
                        v = Q.getColumn(i).subtract(temp).normalize();
                        D.setColumn(i, v);
                    }
                }

                p = new int[dimensions];
                lambda = lambda.multiply(0.0);
                for (int i = 0; i < dimensions; i++) {
                    s.setComponent(i, s0);
                }
            }

            if(maxCalls < functionCalls){
                return X1;
            }
            else {
                double longestStep = Math.abs(s.getComponent(0));
                for (int i = 1; i < dimensions; i++) {
                    if(longestStep < Math.abs(s.getComponent(i))){
                        longestStep = Math.abs(s.getComponent(i));
                    }
                }
                if(longestStep < epsilon){
                    System.out.println(X1);
                    return X1;
                }
            }
        }
    }

    public int getFunctionCalls(){
        return functionCalls;
    }
}
