/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final MinPQ<SearchNode> minPQ;
    private final MinPQ<SearchNode> twins;


    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final int priority;
        private final SearchNode prevSearchNode;

        public SearchNode(Board board, int moves, SearchNode prevSearchNode) {
            this.board = board;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
            this.prevSearchNode = prevSearchNode;
        }


        @Override
        public int compareTo(SearchNode sn) {
            return this.priority - sn.priority;
        }
    }


    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial
     */
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        this.minPQ = new MinPQ<>();
        this.twins = new MinPQ<>();
        minPQ.insert(new SearchNode(initial, 0, null));
        twins.insert(new SearchNode(initial.twin(), 0, null));

        /**
         * 删最低，插相邻，重复，最后剩一个
         */
        while (!minPQ.min().board.isGoal() && !twins.min().board.isGoal()) {
            SearchNode minSearchNode = minPQ.delMin();
            SearchNode minTwins = twins.delMin();
            for (Board b : minSearchNode.board.neighbors()) {
                if (minSearchNode.moves == 0 || !b.equals(minSearchNode.prevSearchNode.board))
                    minPQ.insert(new SearchNode(b, minSearchNode.moves + 1, minSearchNode));
            }
            for (Board b : minTwins.board.neighbors()) {
                if (minTwins.moves == 0 || !b.equals(minTwins.prevSearchNode.board))
                    twins.insert(new SearchNode(b, minTwins.moves + 1, minTwins));
            }
        }
    }

    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        if (minPQ.min().board.isGoal())
            return true;
        return false;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
        if (!isSolvable())
            return -1;
        return minPQ.min().moves;
    }

    /**
     * sequence if boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> stack = new Stack<>();
        SearchNode current = minPQ.min();
        while (current != null) {
            stack.push(current.board);
            current = current.prevSearchNode;
        }
        return stack;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle50.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
