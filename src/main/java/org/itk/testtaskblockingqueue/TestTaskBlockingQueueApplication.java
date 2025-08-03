package org.itk.testtaskblockingqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestTaskBlockingQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskBlockingQueueApplication.class, args);

        BlockingQueue<String> queue = new BlockingQueue<>(5);
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    queue.enqueue("task 1 " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producerThread");

        Thread producerThread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    queue.enqueue("task 2 " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "producerThread1");

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String task = queue.dequeue();
                    System.out.println("consumerThread received "+task);
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumerThread");

        Thread consumerThread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    String task = queue.dequeue();
                    System.out.println("consumerThread1 received "+task);
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumerThread1");

        producerThread.start();
        producerThread1.start();
        consumerThread.start();
        consumerThread1.start();

        try {
            producerThread.join();
            producerThread1.join();
            consumerThread.join();
            consumerThread1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("main end, final size is "+queue.size());
    }

}
