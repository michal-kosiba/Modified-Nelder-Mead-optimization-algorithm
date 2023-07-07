package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.OptWriter;
import optimization.utilities.Vector;

public class Simplex extends Algorithm{
    double s;
    double alpha;
    double beta;
    double gamma;
    double delta;
    double epsilon;
    int maxCalls;

    int functionCalls;

    OptWriter optWriter;
    public Simplex(double s, double alpha, double beta, double gamma, double delta, double epsilon, int maxCalls){
        this.s = s;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.delta = delta;
        this.epsilon = epsilon;
        this.maxCalls = maxCalls;
    }
    @Override
    public Point run(Point startingPoint, Function f) {

        functionCalls = 0;
        //stepWriter = new StepWriter(this.toString(), new DecimalFormat("0.00000"));
        //stepWriter.initialize(new String[]{"X1", "X2", "value"});
        /**
         * Utorzenie tablic przechowujących wierzchołki simpleksu i watrości funkcji f dla każdego z nich
         */
        int dimensions = startingPoint.getDimensions();
        Point[] vertices = new Point[dimensions + 1];
        double[] verticesValues = new double[dimensions + 1];

        /**
         * Zapełnienie obu tablic wartościami początkowymi
         */
        vertices[0] = startingPoint.copy();
        verticesValues[0] = f.call(vertices[0]);
        functionCalls++;
        double[] temp;
        for (int i = 1; i < dimensions + 1; i++) {
            temp = new double[dimensions];
            temp[i-1] = s;
            vertices[i] = vertices[0].copy().shift(new Vector(temp));
            verticesValues[i] = f.call(vertices[i]);
            functionCalls++;
        }

        Point middlePoint;
        Point reflectionPoint;
        Point expansionPoint;
        Point contractionPoint;
        /**
         * Główna pętla algorytmu
         */
        while(true){
            /**
             * Ustalanie punktów z najmniejszą i największą wartością funkcji f
             */
            int minVertex = 0;
            int maxVertex = 0;
            for (int i = 1; i < dimensions + 1; i++) {
                if(verticesValues[minVertex] > verticesValues[i]){
                    minVertex = i;
                }
                if(verticesValues[maxVertex] < verticesValues[i]){
                    maxVertex = i;
                }
            }

            /**
             * Wyznaczanie punktu będącego środkiem ciężkości simpleksu z wyłączeniem punktu o największej wartości
             */
            double[] middlePointLocation = new double[dimensions];
            double[] location;
            for (int i = 0; i < dimensions + 1; i++) {
                if(i != maxVertex){
                    location = vertices[i].getLocation();
                    for (int j = 0; j < dimensions; j++) {
                        middlePointLocation[j] += location[j];
                    }
                }
            }
            for (int k = 0; k < dimensions; k++) {
                middlePointLocation[k] /= dimensions;
            }
            middlePoint = new Point(middlePointLocation);

            /**
             * Wyznaczanie punktu odbicia
             */
            reflectionPoint = middlePoint.copy().
                    shift(new Vector(vertices[maxVertex], middlePoint).multiply(alpha));
            double reflectionResult = f.call(reflectionPoint);
            functionCalls++;
            if(reflectionResult < verticesValues[maxVertex] && reflectionResult >= verticesValues[minVertex]){
                vertices[maxVertex] = reflectionPoint;
                verticesValues[maxVertex] = reflectionResult;
            }
            /**
             * Ekspansja
             */
            else if(reflectionResult < verticesValues[minVertex]){
                expansionPoint = reflectionPoint.copy().
                        shift(new Vector(middlePoint, reflectionPoint).multiply(gamma));
                double expansionResult = f.call(expansionPoint);
                functionCalls++;
                if(expansionResult < reflectionResult) {
                    vertices[maxVertex] = expansionPoint;
                    verticesValues[maxVertex] = expansionResult;
                }
                else{
                    vertices[maxVertex] = reflectionPoint;
                    verticesValues[maxVertex] = reflectionResult;
                }
            }
            /**
             * Zawężenie
             */
            else{
                contractionPoint = middlePoint.copy().
                        shift(new Vector(middlePoint, vertices[maxVertex]).multiply(beta));
                double contractionResult = f.call(contractionPoint);
                functionCalls++;
                if(contractionResult < verticesValues[maxVertex]){
                    vertices[maxVertex] = contractionPoint;
                    verticesValues[maxVertex] = contractionResult;
                }
                /**
                 * Redukcja
                 */
                else {
                    for (int i = 0; i < dimensions + 1; i++) {
                        if(i != minVertex){
                            vertices[i] = vertices[i].shift(new Vector(vertices[i], vertices[minVertex]).multiply(delta));
                            verticesValues[i] = f.call(vertices[i]);
                            functionCalls++;
                        }
                    }
                }
            }

            /**
             * Wyznaczanie najdłuższego boku simpleksa
             */
            double sideLength;
            double maxSideLength = new Vector(vertices[0], vertices[minVertex]).getLength();
            for (int i = 1; i < dimensions + 1; i++) {
                sideLength = new Vector(vertices[i], vertices[minVertex]).getLength();
                if(sideLength > maxSideLength){
                    maxSideLength = sideLength;
                }
            }

            //stepWriter.append(vertices[minVertex]).append(verticesValues[minVertex]).push();
            /**
             * Sprawdzanie warunków stopu
             */
            if(maxSideLength < epsilon || functionCalls >= maxCalls){
                //stepWriter.save();
                if(maxSideLength < epsilon){
                    //System.out.println("Max side length measures less than epsilon!");
                }
                else{
                    //System.out.println("Max function calls preformed!");
                }
                System.out.println(vertices[minVertex]);
                return vertices[minVertex];
            }
        }
    }

    @Override
    public String toString(){
        return "Simplex";
    }

    public int getFunctionCalls(){
        return functionCalls;
    }
}
