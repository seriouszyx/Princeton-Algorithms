/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private int count = 0;

    private Node root;

    private static class Node {
        private Point2D p;   // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree
        private boolean isVerticle; // is the node verticle?

        public Node(Point2D p, boolean isVerticle) {
            this.p = p;
            this.isVerticle = isVerticle;
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(root, p, null);
    }

    private Node insert(Node n, Point2D p, Node pre) {
        if (n == null) {
            count++;
            if (pre == null || !pre.isVerticle)
                return new Node(p, true);
            else
                return new Node(p, false);
        }

        if (n.isVerticle) {
            // compare x-coordinate
            if (p.x() < n.p.x())
                n.lb = insert(n.lb, p, n);
            else
                n.rt = insert(n.rt, p, n);
        } else {
            // compare y-coordinate
            if (p.y() < n.p.y())
                n.lb = insert(n.lb, p, n);
            else
                n.rt = insert(n.rt, p, n);
        }
        return n;
    }

    public boolean contains(Point2D p) {
        Node current = root;
        while (current != null) {
            if (current.p.compareTo(p) == 0)
                return true;
            else if (current.isVerticle) {
                // x
                if (p.x() < current.p.x())
                    current = current.lb;
                else
                    current = current.rt;
            } else {
                // y
                if (p.y() < current.p.y())
                    current = current.lb;
                else
                    current = current.rt;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        KdTree kt = new KdTree();
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        kt.insert(p1);
        kt.insert(p2);
        System.out.println(kt.count);
        System.out.println(kt.contains(p1) && kt.contains(p2));
    }
}
