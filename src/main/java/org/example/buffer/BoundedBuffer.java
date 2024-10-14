package org.example.buffer;

import java.util.Arrays;

class BoundedBuffer<T> {
    private final T[] buffer;
    private int count = 0;
    private int in = 0;
    private int out = -1;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int size) {
        buffer = (T[]) new Object[size];
    }

    public synchronized void put(T item) throws InterruptedException {
        while (count == buffer.length) wait();
        buffer[in++] = item;
        ++count;
        ++out;
        System.out.println("Добавлен " + item + '\n' + Arrays.toString(buffer));
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (count == 0) wait();
        System.out.println("Извлечен " + buffer[out]);
        T result = buffer[out--];
        --count;
        --in;
        buffer[out + 1] = null;
        System.out.println(Arrays.toString(buffer));
        notifyAll();
        return result;
    }
}