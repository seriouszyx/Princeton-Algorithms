import edu.princeton.cs.algs4.StdIn;

/**
 * @description:
 * @author: seriouszyx
 * @create: 2019-03-28 20:20
 **/
public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int num = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }

        for (int i = 0; i < num; i++) {
            System.out.println(rq.dequeue());
        }

    }

}


