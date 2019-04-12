/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

/**
 * 存储输入数据，并实现比较规则
 */
public class Board {

    private final char[] blocks;
    private final int n;
    private int blankPos;

    /**
     * construct a board from an n-by-n array of blocks
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null || blocks[0] == null)
            throw new NullPointerException();
        this.n = blocks.length;
        this.blocks = new char[n * n + 1];
        // 二维转一维
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[++index] = (char) blocks[i][j];
                if (this.blocks[index] == 0)
                    this.blankPos = index;
            }
        }
    }


    /**
     * board dimension n
     * @return
     */
    public int dimension() {
        return this.n;
    }

    /**
     * number of blocks out of place
     * @return
     */
    public int hamming() {
        int count = 0, index = 0;
        for (int i = 1; i < blocks.length; i++) {
            index++;
            if (blocks[index] != i && blocks[index] != 0)
                count++;
        }
        return count;
    }

    /**
     * sum of Manhattan distancces between blocks and goal
     * @return
     */
    public int manhattan() {
        int count = 0, index = 0;
        for (int k = 1; k < blocks.length; k++) {
            int value = blocks[++index];
            if (value != 0) {
                int correctPositionX = value % n == 0 ? value / n : value / n + 1,
                        correctPositionY = (value % n == 0 ? n : value % n);
                int currentPositionX = index % n == 0 ? index / n : index / n + 1,
                        currentPositionY = (index % n == 0 ? n : index % n);
                count += Math.abs(correctPositionX - currentPositionX) +
                        Math.abs(correctPositionY - currentPositionY);
                // System.out.println(
                //         "current:(" + currentPositionX + ", " + currentPositionY + ")" +
                //         "\tcorrect:(" + correctPositionX + ", " + correctPositionY + ")" +
                //         "\tvalue: "+ value + "\tcount: " + count
                // );
            }
        }
        return count;
    }

    /**
     * is this board the goal board?
     * @return
     */
    public boolean isGoal() {
        for (int i = 1; i < blocks.length - 2; i++)
            if (blocks[i] > blocks[i + 1])
                return false;
        return true;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return
     */
    public Board twin() {
        int index1 = -1, index2 = -1;
        if (blocks[1] != 0 && blocks[2] != 0) {
            index1 = 1;
            index2 = 2;
        } else {
            index1 = n + 1;
            index2 = n + 2;
        }
        return new Board(exchangeTwoEle(index1, index2));
    }

    /**
     * exchange two elements and transfer to int[][]
     * @param index1
     * @param index2
     * @return
     */
    private int[][] exchangeTwoEle(int index1, int index2) {
        int[][] bs = new int[n][n];
        int value1 = blocks[index1], value2 = blocks[index2];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                index++;
                if (index == index1)
                    bs[i][j] = value2;
                else if (index == index2)
                    bs[i][j] = value1;
                else
                    bs[i][j] = blocks[index];
            }
        }
        return bs;
    }

    /**
     * does this board equal y?
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        Board b = (Board) y;
        if (!Arrays.equals(this.blocks, b.blocks))
            return false;
        if (this.n != b.n)
            return false;
        return true;

    }

    /**
     * all neighboring boards
     * @return
     */
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();
        int index = blankPos;
        if (index > n) {
            // up
            stack.push(new Board(exchangeTwoEle(index, index - n)));
        }
        if (index + n <= n * n) {
            // down
            stack.push(new Board(exchangeTwoEle(index, index + n)));
        }
        if (index > 0 && (index - 1) % n != 0) {
            // left
            stack.push(new Board(exchangeTwoEle(index, index - 1)));
        }
        if (index < n * n && (index + 1) % n != 1) {
            // right
            stack.push(new Board(exchangeTwoEle(index, index + 1)));
        }

        return stack;
    }

    /**
     * string representation of this board (in the output format sprcified below)
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 1; i <= n * n; i++) {
            sb.append(String.format("%2d ", (int) blocks[i]));
            if (i % n == 0)
                sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        In in = new In("puzzle4x4-14.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.dimension() == n);
        System.out.println("hamming: " + initial.hamming());
        System.out.println("manhattan: " + initial.manhattan());
        System.out.println(initial);
    }
}
