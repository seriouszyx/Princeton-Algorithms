/* *****************************************************************************
 *  Name: seriouszyx
 *  Date: 2019-03-29
 *  Description: a randomized queue is similar to a stack or queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int index = 0;
    private Item[] arr;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    /**
     * is the randomized queue empty?
     * @return
     */
    public boolean isEmpty() {
        return index == 0;
    }

    /**
     *
     * @return the number of items on the randomized queue
     */
    public int size() {
        return index;
    }

    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (index == arr.length)
            resize(arr.length * 2);
        arr[index++] = item;
    }

    /**
     * remove and return a random item
     * @return
     */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int i = StdRandom.uniform(index);
        Item item = arr[i];
        if (i == index - 1) {
            arr[--index] = null;
        } else {
            arr[i] = arr[--index];
            arr[index] = null;
        }
        if (index > 0 && index == arr.length / 4)
            resize(arr.length / 2);
        return item;
    }

    /**
     * return a random item (but do not remove it)
     * @return
     */
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int i = StdRandom.uniform(index);
        return arr[i];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < index; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    /**
     * return an independent iterator over items in random order
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueList();
    }

    private class RandomizedQueueList implements Iterator<Item> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < index;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return arr[i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
