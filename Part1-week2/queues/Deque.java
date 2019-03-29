import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @description: A double-ended queue
 * @author: seriouszyx
 * @create: 2019-03-28 19:38
 **/
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private int size = 0;

    private class Node {
        Item value;
        Node next;
        Node prev;
    }

    /**
     * construct an empty deque
     */
    public Deque() {
        first = new Node();
        first.next = first;
        first.prev = first;
    }

    /**
     * is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @return the number of items on the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * add the item to the front
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node lastfirst = first.next;
        first.next = new Node();
        first.next.value = item;
        first.next.next = lastfirst;
        first.next.prev = first.next;
        lastfirst.prev = first.next;
        size++;
    }

    /**
     * add the item to the last
     * @param item
     */
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node lastrear = first.prev;
        first.prev = new Node();
        first.prev.value = item;
        first.prev.next = first;
        first.prev.prev = lastrear;
        lastrear.next = first.prev;
        size++;
    }

    /**
     * remove the item from the front
     * @return
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node nextfirst = first.next.next;
        Item item = first.next.value;
        first.next = nextfirst;
        nextfirst.prev = first;
        size--;
        return item;
    }

    /**
     * remove the item from the last
     * @return
     */
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node nextlast = first.prev.prev;
        Item item = first.prev.value;
        first.prev = nextlast;
        nextlast.next = first;
        size--;
        return item;
    }

    /**
     * return an iterator over items in order from front to end
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeList();
    }

    private class DequeList implements Iterator<Item> {

        private Node current = first.next;

        @Override
        public boolean hasNext() {
            return current.next != first.next;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.value;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();
        Queue<Integer> std = new Queue<>();
        for (int i = 0; i < 1000; i++) {
            int n = StdRandom.uniform(1000);
            deque.addLast(n);
            std.enqueue(n);
        }
        for (int i = 0; i < 1000; i++) {
            if (!deque.removeFirst().equals(std.dequeue()))
                System.out.println(i);
        }


    }
}


