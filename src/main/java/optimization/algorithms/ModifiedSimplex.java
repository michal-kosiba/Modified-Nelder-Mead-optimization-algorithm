package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Point;
import optimization.utilities.Vector;

public class ModifiedSimplex extends Algorithm{
    double s;
    double alpha;
    double beta;
    double gamma;
    double delta;
    double zeta;
    double epsilon;
    int maxIterations;

    int iterations;

    int functionCalls;
    public ModifiedSimplex(double s, double alpha, double beta, double gamma, double delta, double zeta, double epsilon, int maxIterations){
        this.s = s;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.delta = delta;
        this.epsilon = epsilon;
        this.zeta = zeta;
        this.maxIterations = maxIterations;
    }
    @Override
    public Point run(Point startingPoint, Function f) {

        functionCalls = 0;
        iterations = 0;

        /**
         * Utorzenie tablic przechowujacych wierzcholki sympleksu i watrosci funkcji f dla kazdego z nich
         */
        int dimensions = startingPoint.getDimensions();
        Point[] vertices = new Point[dimensions + 1];
        double[] verticesValues = new double[dimensions + 1];

        /**
         * Zapelnienie obu tablic wartosciami poczatkowymi
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
         * Glowna petla algorytmu
         */
        while(true){
            /**
             * Ustalanie punktow z najmniejsza i najwieksza wartoscia funkcji f
             */

            iterations ++;

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
             * Wyznaczanie punktu bedacego srodkiem ciezkosci sympleksu z wylaczeniem punktu o najwiekszej wartości
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
            // Obliczanie sredniej dlugosci miedzy srodkiem ciezkosci a wierzcholkami simpleksu
            double shift = 0.0;
            for (int i = 0; i < vertices.length; i++) {
                if(i != maxVertex){
                    shift += new Vector(middlePoint, vertices[i]).getLength();
                }
            }
            shift /= (vertices.length - 1);

            // Przesuniecie srodka ciezkosci o wektor znakow gradientu przemnozony przez obliczona wczesniej wartosc
            Vector shiftDirection = f.gradientSign(middlePoint).reverse().normalize();
            middlePoint = middlePoint.shift(shiftDirection.multiply(shift).multiply(zeta));

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
             * Zawezenie
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
             * Wyznaczanie najdluzszego boku sympleksu
             */
            double sideLength;
            double maxSideLength = new Vector(vertices[0], vertices[minVertex]).getLength();
            for (int i = 1; i < dimensions + 1; i++) {
                sideLength = new Vector(vertices[i], vertices[minVertex]).getLength();
                if(sideLength > maxSideLength){
                    maxSideLength = sideLength;
                }
            }


            /**
             * Sprawdzanie warunków stopu
             */
            if(maxSideLength < epsilon || iterations >= maxIterations){
                return vertices[minVertex];
            }
        }
    }

    @Override
    public String toString(){
        return "ModifiedSimplex";
    }

    public int getFunctionCalls(){
        return functionCalls;
    }
}