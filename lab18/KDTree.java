import java.util.List;
import java.util.Comparator;


public class KDTree<Point extends Comparable<Point>> implements PointSet {

    List<Point> points;
    KDTreeNode root;


    /* Constructs a KDTree using POINTS. You can assume POINTS contains at least one
       Point object. */
    public KDTree(List<Point> points) {
        this.points = points;
        for (Point point : points) {
            insert(this.root, point);
        }
    }

    private KDTreeNode insert(KDTreeNode kdTreeNode, Point point) {
        if (kdTreeNode == null) {
            kdTreeNode = new KDTreeNode(point);
        } else if (point.compareTo(kdTreeNode.point) < 0) {
            insert(kdTreeNode.left, point);
        } else if (point.compareTo(kdTreeNode.point) > 0) {
            insert(kdTreeNode.right, point);
        }
        return kdTreeNode;
    }



    /*

    You might find this insert helper method useful when constructing your KDTree!
    Think of what arguments you might want insert to take in. If you need
    inspiration, take a look at how we do BST insertion!

    private KDTreeNode insert(...) {
        ...
    }

    */

    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in O(log N) time on average, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        // TODO: YOUR CODE HERE
        return null;
    }

    private class KDTreeNode {

        private Point point;
        private KDTreeNode left;
        private KDTreeNode right;

        // If you want to add any more instance variables, put them here!

        KDTreeNode(Point p) {
            this.point = p;
        }

        KDTreeNode(Point p, KDTreeNode left, KDTreeNode right) {
            this.point = p;
            this.left = left;
            this.right = right;
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
