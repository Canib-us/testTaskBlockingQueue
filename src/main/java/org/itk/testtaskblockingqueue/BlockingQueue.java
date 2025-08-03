package org.itk.testtaskblockingqueue;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue <T>{
    private final Queue<T> queue;
    private final int capacity;
    private final Object lock = new Object();

    public BlockingQueue(int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException();
        }
        this.queue = new LinkedList<T>();
        this.capacity = capacity;
    }

    public void enqueue(T t) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() >= capacity) {
                lock.wait();
            }
            queue.offer(t);
            System.out.println("Enqueued " + t+", size="+queue.size());
            lock.notifyAll();
        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }
            T t = queue.poll();
            System.out.println("Dequeued " + t + ", size=" + queue.size());
            lock.notifyAll();
            return t;
        }
    }

    public int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
}
