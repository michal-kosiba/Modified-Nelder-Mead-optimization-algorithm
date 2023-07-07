package optimization.algorithms;

import optimization.utilities.Function;
import optimization.utilities.Point;

public abstract class Algorithm {
    public abstract Point run(Point startingPoint, Function f);
}
