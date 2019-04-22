/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private SET<Point2D> set;

    private static final double RADIUS = 0.01;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        this.set = new SET<>();
    }

    /**
     * is the set empty?
     * @return
     */
    public boolean isEmpty() {
        return set.size() == 0;
    }

    /**
     * number of points in the set
     * @return
     */
    public int size() {
        return set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        set.add(p);
    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(RADIUS);
        for (Point2D p : set) {
            p.draw();
        }
    }

    /**
     * all points that are inside the retangle (or on the boundary)
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : set) {
            if (rect.contains(p))
                queue.enqueue(p);
        }
        return queue;
    }


    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (isEmpty())
            return null;
        Point2D res = set.min();
        double min = Double.POSITIVE_INFINITY;
        for (Point2D point2D : set) {
            if (p.distanceSquaredTo(point2D) < min) {
                res = p;
                min = p.distanceSquaredTo(point2D);
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }
}
