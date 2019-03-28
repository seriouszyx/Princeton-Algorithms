/**
 * @description:
 * @author: seriouszyx
 * @create: 2019-03-28 20:20
 **/
public class Permutation {

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.isEmpty());
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);
        for (Integer i : deque) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        for (Integer i : deque) {
            System.out.print(i + " ");
        }

    }

}


