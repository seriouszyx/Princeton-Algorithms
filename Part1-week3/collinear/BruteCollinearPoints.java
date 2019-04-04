/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class BruteCollinearPoints {

    /** Record the linesegments */
    private ArrayList<LineSegment> list;

    /**
     * find all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
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
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int t = k + 1; t < N; t++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                            && points[i].slopeTo(points[k]) == points[i].slopeTo(points[t]))
                            addLineSegment(points, i, j, k, t);
                    }
                }
            }
        }
    }

    /**
     * Add the line segment to list
     * @param points
     * @param i
     * @param j
     * @param k
     * @param t
     */
    private void addLineSegment(Point[] points, int i, int j, int k, int t) {
        Point[] ps = new Point[]{points[i], points[j], points[k], points[t]};
        Point min = ps[0], max = ps[0];
        for (int index = 1; index < ps.length; index++) {
            if (min.compareTo(ps[index]) > 0)
                min = ps[index];
            if (max.compareTo(ps[index]) < 0)
                max = ps[index];
        }
        list.add(new LineSegment(min, max));
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

    }
}
