import java.util.List;

public class NaivePointSet implements PointSet {
    public List<Point> points;
    /* Constructs a NaivePointSet using POINTS. You can assume POINTS contains at
       least one Point object. */
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in Theta(N) time, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        Point given = new Point(x, y);
        Point closest = points.get(0);
        double distance = Point.distance(given, closest);
        for (Point point : points) {
            if (distance > Point.distance(given, point)) {
                closest = point;
                distance = Point.distance(given, point);
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
