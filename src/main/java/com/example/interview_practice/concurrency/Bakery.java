package com.example.interview_practice.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bakery {

    // it's public to be able to test
    public final Queue<String> shelf = new LinkedList<>();
    private static final int MAX_SIZE = 5;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition makeBread = lock.newCondition();
    private final Condition consumeBread = lock.newCondition();

    public void addBread() throws InterruptedException {
        lock.lock();
        try {
            while (shelf.size() == MAX_SIZE) makeBread.await();
            shelf.add("Bread");
            consumeBread.signal();
        } finally {
            lock.unlock();
        }
    }

    public void takeBread() throws InterruptedException {
        lock.lock();
        try {
            // when await calls thread will be paused until in another thread someone on same condition call signal()
            while (shelf.isEmpty()) consumeBread.await();
            shelf.poll();
            makeBread.signal();
        } finally {
            lock.unlock();
        }
    }
}
