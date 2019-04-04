/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    /** Record the linesegments */
    private ArrayList<LineSegment> list;

    /**
     * find all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }


        list = new ArrayList<>();
        int N = points.length;
        Arrays.sort(points);

        for (int i = 0; i < N - 1; i++) {
            /** get the smallest point */
            Arrays.sort(points);
            Point min = points[i];
            /** sort as the points' slope */
            Arrays.sort(points, i, N, points[i].slopeOrder());

            Point max = null;
            int count = 0;

            for (int j = i + 1; j < N - 1; j++) {
                if (min.slopeTo(points[j]) == min.slopeTo(points[j + 1])) {
                    count++;
                    max = points[j + 1];
                } else if (count != 2) {
                    count = 0;
                }
                if (count >= 2) {
                    count = 0;
                    list.add(new LineSegment(min, max));
                }
            }
        }
    }


    /**
     * the number of line segments
     * @return
     */
    public int numberOfSegments() {
        return list.size();
    }

    /**
     * the line segments
     * @return
     */
    public LineSegment[] segments() {
        LineSegment[] ans = new LineSegment[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
