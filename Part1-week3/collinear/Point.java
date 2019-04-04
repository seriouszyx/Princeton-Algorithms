/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null)
            throw new NoSuchElementException();
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        else if (this.y == that.y)
            return +0;
        else
            return (this.y - that.y) * 1.0 / (this.x - that.x);

    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null)
            throw new NoSuchElementException();
        if (this.x == that.x && this.y == that.y)
            return 0;
        if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeCompare();
    }

    private class SlopeCompare implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            if (o1 == null || o2 == null)
                throw new NoSuchElementException();
            if (slopeTo(o1) == Double.NEGATIVE_INFINITY && slopeTo(o2) == Double.NEGATIVE_INFINITY)
                return 0;
            else if (slopeTo(o1) == Double.POSITIVE_INFINITY && slopeTo(o2) == Double.POSITIVE_INFINITY)
                return 0;
            else if (slopeTo(o1) == Double.POSITIVE_INFINITY && slopeTo(o2) == Double.NEGATIVE_INFINITY)
                return 1;
            else if (slopeTo(o1) == Double.NEGATIVE_INFINITY && slopeTo(o2) == Double.POSITIVE_INFINITY)
                return -1;
            else if (slopeTo(o1) - slopeTo(o2) > 0)
                return 1;
            else if (slopeTo(o1) - slopeTo(o2) < 0)
                return -1;
                // return slopeTo(o1) - slopeTo(o2) < 0 ? -1 : 1;
            return 0;
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p1 = new Point(0, 10);
        Point p2 = new Point(10, 0);
        System.out.println(p1.slopeTo(p2) == p2.slopeTo(p1));
        Point p3 = new Point(0, 20);
        System.out.println(p3.slopeOrder().compare(p1, p2));
    }
}
