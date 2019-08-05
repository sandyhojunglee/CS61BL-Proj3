import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {

    List<Point> points;
    KDTreeNode root;
    int treeHeight;

    /* Constructs a KDTree using POINTS. You can assume POINTS contains at least one
       Point object. */
    public KDTree(List<Point> points) {
        this.points = points;
        for (Point point : points) {
            root = insert(this.root, point, true);
        }
    }

    /*

    You might find this insert helper method useful when constructing your KDTree!
    Think of what arguments you might want insert to take in. If you need
    inspiration, take a look at how we do BST insertion!

    */

    private KDTreeNode insert(KDTreeNode kdTreeNode, Point point, Boolean bool) {
        if (kdTreeNode == null) {
            kdTreeNode = new KDTreeNode(point);
            //if true: compare x vlaues
        } else if (bool) {
            if (point.getX() - kdTreeNode.point.getX() < 0) {
                treeHeight ++;
                kdTreeNode.left = insert(kdTreeNode.left, point, false);
            } else if (point.getX() - kdTreeNode.point.getX() > 0) {
                treeHeight++;
                kdTreeNode.right = insert(kdTreeNode.right, point, false);
            }
        } else {
            if (point.getY() - kdTreeNode.point.getY() < 0) {
                treeHeight ++;
                kdTreeNode.left = insert(kdTreeNode.left, point, true);
            } else if (point.getY() - kdTreeNode.point.getY() > 0) {
                treeHeight ++;
                kdTreeNode.right = insert(kdTreeNode.right, point, true);
            }
        }
        return kdTreeNode;
    }

    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in O(log N) time on average, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        // TODO: YOUR CODE HERE
        Point insert = new Point(x, y);
        KDTreeNode n = this.root;
        //idk if this is right
        



        // find distance from root to given point
        // set this distance as "best"
        // choose which side to traverse on tree:
        //  choose left if "given" point is less than current roote / best distaance
        // find new distnce
        // if new distance < bewDist, replace best distance with new distance
        // keep exploring "good" children


        return points.get(1);
    }

    public Point nearestHelper(KDTreeNode n, Point goal, Point best) {
        return null;
    }

    private class KDTreeNode {

        private Point point;
        private KDTreeNode left;
        private KDTreeNode right;

        // If you want to add any more instance variables, put them here!

        KDTreeNode(Point p) {
            this.point = p;
            this.left = null;
            this.right = null;
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
