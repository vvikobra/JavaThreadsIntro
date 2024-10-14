package org.example.buffer;

import java.util.concurrent.CountDownLatch;

public class BoundedBufferTest {

    public static void main(String[] args) throws InterruptedException {
        int threadsCount = 10;

        BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>(10);

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch finishSignal = new CountDownLatch(threadsCount * 2);

        for (int i = 1; i <= threadsCount; i++) {
            new Thread(new Consumer(startSignal, finishSignal, boundedBuffer)).start();

        }

        for (int i = 1; i <= threadsCount; i++) {
            new Thread(new Producer(startSignal, finishSignal, boundedBuffer)).start();

        }

        startSignal.countDown();
        finishSignal.await();

    }

}
