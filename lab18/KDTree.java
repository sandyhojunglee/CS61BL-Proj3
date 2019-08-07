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
            } else if (point.getX() - kdTreeNode.point.getX() == 0) {
                kdTreeNode.right = insert(kdTreeNode.right, point, !orientation, false);
            }
        } else {
            if (point.getY() - kdTreeNode.point.getY() < 0) {
                kdTreeNode.left = insert(kdTreeNode.left, point, !orientation, true);
            } else if (point.getY() - kdTreeNode.point.getY() > 0) {
                kdTreeNode.right = insert(kdTreeNode.right, point, !orientation,true);
            } else if (point.getY() - kdTreeNode.point.getY() == 0) {
                kdTreeNode.right = insert(kdTreeNode.right, point, !orientation, true);
            }
        }
        return kdTreeNode;
    }

    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in O(log N) time on average, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);

        return nearestHelper(root, goal, root.point);

    }

    public Point nearestHelper(KDTreeNode current, Point goal, Point best) {
        if (current == null) {
            return best;
        }

        double bestDistance = Point.distance(goal, best);
        double currentDistance = Point.distance(goal, current.point);

        KDTreeNode goodSide;
        KDTreeNode badSide;

        if (currentDistance < bestDistance) {
            best = current.point;
        }
        if (comparingPoints(goal, current.point, current.orientation) <= 0) {
            goodSide = current.left;
            badSide = current.right;
        } else {
            goodSide = current.right;
            badSide = current.left;
        }
        best = nearestHelper(goodSide, goal, best);

        if (current.orientation == horizontal) {
            Point compare = new Point(current.point.getX(), goal.getY());
            if (Point.distance(compare, goal) < Point.distance(best, goal)) {
                best = nearestHelper(badSide, goal, best);
            }
        } else if (current.orientation == vertical) {
            Point compare1 = new Point(goal.getX(), current.point.getY());
            if (Point.distance(compare1, goal) < Point.distance(best, goal)) {
                best = nearestHelper(badSide, goal, best);
            }

        } return best;
    }

    private double comparingPoints (Point a, Point b,boolean orientation){
        if (orientation == horizontal) {
            return a.getX() - b.getY();
        } else {
            return a.getY() - b.getX();
        }
//            Point newPoint = new Point(best.getX(), goal.getY());
//            double darkDistance = Point.distance(goal, newPoint);
//            if (darkDistance < bestDistance) {
//                //return nearestHelper();
//            }
//
//        } else {
//            Point newPoint = new Point(given.getX(), best.getY());
//
//        }
    }


    private class KDTreeNode {

        private Point point;
        private KDTreeNode left;
        private KDTreeNode right;
        private boolean orientation;

        // If you want to add any more instance variables, put them here!

        KDTreeNode(Point p) {
            this.point = p;
            this.left = null;
            this.right = null;
            this.orientation = true;
        }

        KDTreeNode(Point p, KDTreeNode left, KDTreeNode right) {
            this.point = p;
            this.left = left;
            this.right = right;
        }

        KDTreeNode(Point p, boolean orientation) {
            this.point = p;
            this.left = null;
            this.right = null;
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
        int comparePoints(Point p, Point n, Boolean orientation) {
            return 0;
        }

    }

}
