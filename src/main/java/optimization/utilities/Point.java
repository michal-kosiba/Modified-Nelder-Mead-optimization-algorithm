package optimization.utilities;

import java.util.Arrays;

public class Point {
    private double[] location;

    public int getDimensions() {
        return location.length;
    }

    public double[] getLocation() {
        return Arrays.copyOf(location, location.length);
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    /**
     * Constructor that creates a point representing given location
     * @param location given location
     */

    public Point(double... location){
        this.location = location;
    }

    /**
     * Constructor that creates a point of given dimensions, and sets it as origin of the coordinate system
     * @param dimensions given dimensions
     */
    public Point(int dimensions){
        this.location = new double[dimensions];
        Arrays.fill(this.location, 0.0);
    }


    public Point shift(Vector vector){
        double[] newLocation = new double[location.length];
        for (int i = 0; i < location.length; i++) {
            newLocation[i] = this.location[i] + vector.getComponent(i);
        }
        return new Point(newLocation);
    }

    public Point copy(){
        return new Point(location.clone());
    }

    @Override
    public String toString(){
        return Arrays.toString(location);
    }

    public Vector toVector(){
        return new Vector(Arrays.copyOf(location, location.length));
    }
}
