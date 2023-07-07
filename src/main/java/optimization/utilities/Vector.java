package optimization.utilities;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int dimensions){
        components = new double[dimensions];
    }

    public Vector(Point A, Point B){
        double[] a = A.getLocation();
        double[] b = B.getLocation();
        components = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            components[i] = b[i] - a[i];
        }
    }

    public Vector(double... components){
        this.components = components;
    }

    public int getDimensions(){
        return this.components.length;
    }
    public double getComponent(int index){
        return components[index];
    }

    public double[] getComponents(){
        return Arrays.copyOf(components, components.length);
    }
    public void setComponent(int componentNumber, double value){
        if(componentNumber > components.length || componentNumber < 0){
            System.out.printf("Out of border: requested component %d while there are %d components", componentNumber, components.length);
            System.exit(-1);
        }
        components[componentNumber] = value;
    }

    public void addToComponent(int componentNumber, double value){
        if(componentNumber > components.length || componentNumber < 0){
            System.out.printf("Out of border: requested component %d while there are %d components", componentNumber, components.length);
            System.exit(-1);
        }
        components[componentNumber] += value;
    }

    public void multiplyComponent(int componentNumber, double value){
        if(componentNumber > components.length || componentNumber < 0){
            System.out.printf("Out of border: requested component %d while there are %d components", componentNumber, components.length);
            System.exit(-1);
        }
        components[componentNumber] *= value;
    }
    public Vector reverse(){
        double[] reversedComponents = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            reversedComponents[i] = -components[i];
        }
        return new Vector(reversedComponents);
    }

    public Vector normalize(){
        double lengthReciprocal = 1.0 / this.getLength();
        double[] normalizedComponents = Arrays.copyOf(components, components.length);
        for (int i = 0; i < components.length; i++) {
            normalizedComponents[i] = components[i] * lengthReciprocal;
        }
        return new Vector(normalizedComponents);
    }

    public String toString(){
        return Arrays.toString(components).replace('[', '<').replace(']', '>');
    }

    public Vector multiply(double factor){
        double[] multipliedComponents = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            multipliedComponents[i] = factor * components[i];
        }
        return new Vector(multipliedComponents);
    }

    public Vector add(Vector vector){
        double[] newComponents = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            newComponents[i] = this.components[i] + vector.getComponent(i);
        }
        return new Vector(newComponents);
    }

    public Vector subtract(Vector vector){
        return add(vector.multiply(-1.0));
    }

    public double getLength(){
        double sum = 0;
        for (double component:components) {
            sum += Math.pow(component, 2);
        }
        return Math.sqrt(sum);
    }

    public double dotProduct(Vector vector){
        double result = 0.0;
        double[] components = vector.getComponents();
        for (int i = 0; i < components.length; i++) {
            result += this.components[i] * components[i];
        }
        return result;
    }

    public Point toPoint(){
        return new Point(Arrays.copyOf(components, components.length));
    }
}
