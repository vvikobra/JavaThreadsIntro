package org.example.buffer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Producer implements Runnable{

    private final CountDownLatch startSignal;
    private final CountDownLatch finishSignal;
    private final BoundedBuffer buffer;

    public Producer(CountDownLatch startSignal, CountDownLatch finishSignal, BoundedBuffer buffer) {
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            startSignal.await();
            buffer.put(random.nextInt(0, 10));
            finishSignal.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
