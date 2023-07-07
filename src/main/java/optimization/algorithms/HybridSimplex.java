package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.OptWriter;
import optimization.utilities.Vector;

import java.text.DecimalFormat;

public class HybridSimplex extends Algorithm{
    double alpha;
    double beta;
    double gamma;
    double delta;
    double epsilon;
    int maxCalls;

    int functionCalls;
    int gradientCalls;

    OptWriter optWriter;
    public HybridSimplex(double alpha, double beta, double gamma, double delta, double epsilon, int maxCalls){
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.delta = delta;
        this.epsilon = epsilon;
        this.maxCalls = maxCalls;
        this.functionCalls = 0;
        optWriter = new OptWriter(this.toString(), new DecimalFormat("0.00000"));
        optWriter.initialize(new String[]{"X1", "X2", "value", "omega"});
    }
    @Override
    public Point run(Point startingPoint, Function f) {

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
        double[] temp;
        for (int i = 1; i < dimensions + 1; i++) {
            temp = new double[dimensions];
            temp[i-1] = 1.0;
            vertices[i] = vertices[0].copy().shift(new Vector(temp));
            verticesValues[i] = f.call(vertices[i]);
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
            Vector reversedGradient = f.gradient(middlePoint).reverse();
            Vector d = new Vector(vertices[maxVertex], middlePoint);
            double omega = d.dotProduct(reversedGradient) / (d.getLength() * reversedGradient.getLength()) + 1;
            reflectionPoint = middlePoint.copy().
                    shift(d.multiply(alpha).multiply(omega));
            double reflectionResult = f.call(reflectionPoint);
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

            optWriter.append(vertices[minVertex]).append(verticesValues[minVertex]).append(omega).push();
            functionCalls++;
            /**
             * Sprawdzanie warunków stopu
             */
            if(maxSideLength < epsilon || functionCalls >= maxCalls){
                optWriter.save();
                if(maxSideLength < epsilon){
                    System.out.println("Max side length measures less than epsilon!");
                }
                else{
                    System.out.println("Max function calls preformed!");
                }
                return vertices[minVertex];
            }
        }
    }

    @Override
    public String toString(){
        return "HybridSimplex";
    }

    public Vector calculateV(Point maxPoint, Point middlePoint, Function f){
        Vector d = new Vector(maxPoint, middlePoint);
        Vector v = f.gradient(middlePoint).
                reverse().
                normalize().
                multiply(d.getLength());
        return v;
    }
}