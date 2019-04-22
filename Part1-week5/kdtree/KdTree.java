/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private int count = 0;

    private Node root;

    private static class Node {
        private Point2D p;   // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree
        private boolean isVerticle; // is the node verticle?

        public Node(Point2D p, boolean isVerticle, RectHV rect) {
            this.p = p;
            this.isVerticle = isVerticle;
            this.rect = rect;
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
        root = insert(root, p, null, 0);
    }

    private Node insert(Node n, Point2D p, Node pre, int direction) {
        if (n == null) {
            count++;
            if (direction == 0)
                return new Node(p, true, new RectHV(0, 0, 1, 1));

            RectHV preRect = pre.rect;
            if (direction == -1) {
                if (!pre.isVerticle)    // down
                    return new Node(p, true, new RectHV(pre.rect.xmin(), pre.rect.ymin(),
                                                        pre.rect.xmax(), pre.p.y()));
                else    // left
                    return new Node(p, false, new RectHV(pre.rect.xmin(), pre.rect.ymin(),
                                                        pre.p.x(), pre.rect.ymax()));
            } else if (direction == 1) {
                if (!pre.isVerticle)    // up
                    return new Node(p, true, new RectHV(pre.rect.xmin(), pre.p.y(),
                                                        pre.rect.xmax(), pre.rect.ymax()));
                else    // right
                    return new Node(p, false, new RectHV(pre.p.x(), pre.rect.ymin(),
                                                         pre.rect.xmax(), pre.rect.ymax()));
            }
        } else {
            int cmp = 0;
            if (n.isVerticle)
                cmp = p.x() < n.p.x() ? -1 : 1;
            else
                cmp = p.y() < n.p.y() ? -1 : 1;
            // 去重
            if (p.equals(n.p))
                cmp = 0;

            if (cmp == -1)
                n.lb = insert(n.lb, p, n, cmp);
            else if (cmp == 1)
                n.rt = insert(n.rt, p, n, cmp);
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

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        range(rect, root, queue);
        return queue;
    }

    private void range(RectHV rect, Node x, Queue<Point2D> queue) {
        if (x == null)
            return;
        if (rect.contains(x.p))
            queue.enqueue(x.p);
        if (x.lb != null && x.lb.rect.intersects(rect))
            range(rect, x.lb, queue);
        if (x.rt != null && x.rt.rect.intersects(rect))
            range(rect, x.rt, queue);
    }


    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return null;
        return nearest(p, root, root.p);
    }

    private Point2D nearest(Point2D goal, Node x, Point2D nearest) {
        if (x == null)
            return nearest;

        if (goal.distanceSquaredTo(x.p) < goal.distanceSquaredTo(nearest))
            nearest = x.p;

        int cmp = 0;
        if (x.isVerticle)
            cmp = goal.x() < x.p.x() ? -1 : 1;
        else
            cmp = goal.y() < x.p.y() ? -1 : 1;
        if (x.p.equals(goal))
            cmp = 0;

        if (cmp == -1) {
            nearest =  nearest(goal, x.lb, nearest);
            if (x.rt != null && x.rt.rect.distanceSquaredTo(goal) < nearest.distanceSquaredTo(goal))
                nearest = nearest(goal, x.rt, nearest);
        } else if (cmp == 1) {
            nearest =  nearest(goal, x.rt, nearest);
            if (x.lb != null && x.lb.rect.distanceSquaredTo(goal) < nearest.distanceSquaredTo(goal))
                nearest = nearest(goal, x.lb, nearest);
        }

        return nearest;
    }

    public void draw()
    {
        draw(root);
    }

    private void draw(Node x)
    {
        if (x == null) return;
        draw(x.lb);
        draw(x.rt);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        StdDraw.setPenRadius();
        // draw the splitting line segment
        if (x.isVerticle)
        {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        }
        else
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
    }


    public static void main(String[] args) {
        // KdTree kt = new KdTree();
        // Point2D p1 = new Point2D(0.7, 0.2);
        // Point2D p2 = new Point2D(0.5, 0.4);
        // kt.insert(p1);
        // kt.insert(p2);
        // System.out.println(kt.count);
        // System.out.println(kt.contains(p1) && kt.contains(p2));

    }
}
