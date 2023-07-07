package optimization.utilities;

public abstract class Function {
    /**
     * Constants representing unknown gradient vector components
     */
    protected final static double NAN = 0.0d / 0.0;
    protected final static double POSITIVE_INFINITY = 1.0 / 0.0;
    protected final static double NEGATIVE_INFINITY = -1.0 / 0.0;

    public abstract double call(Point point);

    public abstract Vector gradient(Point point);

    public Vector gradientSign(Point point) {
        double[] result = gradient(point).getComponents();
        for (int i = 0; i < result.length; i++) {
            if (Double.isNaN(result[i]) || result[i] == POSITIVE_INFINITY || result[i] == NEGATIVE_INFINITY || result[i] == 0.0) {
                result[i] = 0.0;
            } else if (result[i] > 0) {
                result[i] = 1.0;
            } else {
                result[i] = -1.0;
            }
        }
        return new Vector(result);
    }
}
