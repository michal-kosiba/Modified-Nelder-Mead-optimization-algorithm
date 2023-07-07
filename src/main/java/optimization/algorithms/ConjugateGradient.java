package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.OptWriter;
import optimization.utilities.Vector;

import java.text.DecimalFormat;

public class ConjugateGradient extends Algorithm{
    double h0;
    double epsilon;
    double beta;
    int maxIterations;
    int iterations = 0;

    OptWriter optWriter;

    public ConjugateGradient(double h0, double epsilon, int maxIterations) {
        this.h0 = h0;
        this.epsilon = epsilon;
        this.maxIterations = maxIterations;
        optWriter = new OptWriter(this.toString(), new DecimalFormat("0.00000"));
        optWriter.initialize(new String[]{"X1", "X2", "dx1", "dx2"});
    }

    @Override
    public Point run(Point startingPoint, Function f) {
        iterations = 0;
        Point X0 = startingPoint.copy();
        Point X1;
        Vector d = f.gradient(X0).reverse();
        while(true){
            iterations++;
            X1 = X0.shift(d.multiply(h0));
            optWriter.append(X1).append(d).push();
            if(new Vector(X0,X1).getLength() < epsilon || iterations >= maxIterations){
                optWriter.save();
                return X1;
            }
            beta = Math.pow(f.gradient(X1).getLength(), 2) / Math.pow(f.gradient(X0).getLength(), 2);
            d = f.gradient(X1).reverse();
            d.add(d.multiply(beta));
            X0 = X1;
        }
    }

    @Override
    public String toString(){
        return "Conjugate-Gradient";
    }

    public int getFunctionCalls(){
        return iterations;
    }
}

