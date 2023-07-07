package optimization.utilities;

import java.util.Arrays;

public class RandomPointFactory {
    int pointDimension;
    double[] minValues;
    double[] maxValues;

    public RandomPointFactory(int pointDimension, double ... borders){
        this.pointDimension = pointDimension;

        if(borders.length != pointDimension*2){
            System.out.println("Nieprawidłowa ilość ograniczeń w stosunku do wymiaru punktu.");
            System.exit(-1);
        }

        minValues = new double[pointDimension];
        maxValues = new double[pointDimension];
        for (int i = 0,j = 0; j < pointDimension; i+=2, j++) {
            minValues[j] = borders[i];
            maxValues[j] = borders[i+1];
        }
    }

    public Point randomPoint(){
        double[] randomPointLocation = new double[pointDimension];
        for (int i = 0; i < pointDimension; i++) {
            randomPointLocation[i] = Math.random() * (maxValues[i] - minValues[i]) + minValues[i];
        }
        return new Point(randomPointLocation);
    }

    public Point[] randomPoints(int numberOfPoints){
        Point[] randomPoints = new Point[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            randomPoints[i] = randomPoint();
        }
        return randomPoints;
    }
}
