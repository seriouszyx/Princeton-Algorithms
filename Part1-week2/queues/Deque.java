import java.util.Iterator;

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
        first.next = first.prev = first;
    }

    /**
     * is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return first.next == first.prev;
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
            Item item = current.value;
            current = current.next;
            return item;
        }
    }
}


