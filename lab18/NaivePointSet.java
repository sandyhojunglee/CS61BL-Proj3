import java.util.List;

public class NaivePointSet implements PointSet {
    // TODO: Instance variables here!
    List<Point> list;

    /* Constructs a NaivePointSet using POINTS. You can assume POINTS contains at
       least one Point object. */
    public NaivePointSet(List<Point> points) {
        // TODO: YOUR CODE HERE
        list = points;
    }

    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in Theta(N) time, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        // TODO: YOUR CODE HERE
        Point input = new Point(x, y);
        Point closest = new Point(0,0);
        double shortest = 1000000.0;
        for (Point p :list){
            double compare = Point.distance(p, input);
            if (compare < shortest) {
                shortest = compare;
                closest = p;
            }
        }
        return closest;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }
}
