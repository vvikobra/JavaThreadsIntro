package org.example.buffer;

import java.util.concurrent.CountDownLatch;

public class Consumer implements Runnable{

    private final CountDownLatch startSignal;
    private final CountDownLatch finishSignal;
    private final BoundedBuffer buffer;

    public Consumer(CountDownLatch startSignal, CountDownLatch finishSignal, BoundedBuffer buffer) {
        this.startSignal = startSignal;
        this.finishSignal = finishSignal;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            buffer.take();
            finishSignal.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
