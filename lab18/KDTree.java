import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {

    List<Point> points;
    KDTreeNode root;
    final boolean horizontal = false;
    final boolean vertical = true;


    /* Constructs a KDTree using POINTS. You can assume POINTS contains at least one
       Point object. */
    public KDTree(List<Point> points) {
        this.points = points;

        for (Point point : points) {
            if (point == points.get(0)) {
                root = new KDTreeNode(point, horizontal);
            }
            else {
                insert(this.root, point, horizontal, true);
            }

        }
    }

    /*

    You might find this insert helper method useful when constructing your KDTree!
    Think of what arguments you might want insert to take in. If you need
    inspiration, take a look at how we do BST insertion!

    */

    private KDTreeNode insert(KDTreeNode kdTreeNode, Point point, Boolean orientation, Boolean bool) {
        if (kdTreeNode == null) {
            kdTreeNode = new KDTreeNode(point, orientation);
            //if true: compare x vlaues
        } else if (bool) {
            if (point.getX() - kdTreeNode.point.getX() < 0) {
                kdTreeNode.left = insert(kdTreeNode.left, point, !orientation, false);
            } else if (point.getX() - kdTreeNode.point.getX() > 0) {
                kdTreeNode.right = insert(kdTreeNode.right, point, !orientation, false);
            }
        } else {
            if (point.getY() - kdTreeNode.point.getY() < 0) {
                kdTreeNode.left = insert(kdTreeNode.left, point, !orientation, true);
            } else if (point.getY() - kdTreeNode.point.getY() > 0) {
                kdTreeNode.right = insert(kdTreeNode.right, point, !orientation,true);
            }

        }
        return kdTreeNode;
    }

    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in O(log N) time on average, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        Point given = new Point(x, y);

        return nearestHelper(root, given, root.point);

    }

    public Point nearestHelper(KDTreeNode current, Point given, Point best) {

        double bestDistance = Point.distance(given, best);
        double currentDistance = Point.distance(given, best);

        if (current.orientation == horizontal) {
            Point newPoint = new Point(best.getX(), given.getY());
            double darkDistance = Point.distance(given, newPoint);
            if (darkDistance < bestDistance) {
                return nearestHelper()
            }

        } else {
            Point newPoint = new Point(given.getX(), best.getY());

        }

        return new Point(1, 2);

    }


    // find distance from root to given point
    // set this distance as "best"
    // choose which side to traverse on tree:
    //  choose left if "given" point is less than current roote / best distaance
    // find new distnce
    // if new distance < bewDist, replace best distance with new distance
    // keep exploring "good" children



    private class KDTreeNode {

        private Point point;
        private KDTreeNode left;
        private KDTreeNode right;
        private boolean orientation;

        // If you want to add any more instance variables, put them here!

        KDTreeNode(Point p) {
            this.point = p;
        }

        KDTreeNode(Point p, KDTreeNode left, KDTreeNode right) {
            this.point = p;
            this.left = left;
            this.right = right;
        }

        KDTreeNode(Point p, boolean orientation) {
            this.point = p;
            this.orientation = orientation;
        }

        Point point() {
            return point;
        }

        KDTreeNode left() {
            return left;
        }

        KDTreeNode right() {
            return right;
        }

        // If you want to add any more methods, put them here!

    }
}
