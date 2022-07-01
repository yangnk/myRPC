package com.yangnk.others.ths;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangningkai
 * @create 2022-06-29 23:02
 **/

public class Solution1b {
    static Lock lock = new ReentrantLock();
    static Condition condition1 = lock.newCondition();
    static Condition condition2 = lock.newCondition();
    static Condition condition3 = lock.newCondition();
    int state = 0;


    void print(String s, int num, Condition cur, Condition next) {
        while (true) {
            lock.lock();
            while (state % 3 != num) {
                try {
                    cur.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(s);
            state++;
            next.signal();
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Solution1b solution1b = new Solution1b();
        new Thread(() -> solution1b.print("a", 0, condition1, condition2)).start();
        new Thread(() -> solution1b.print("b", 1, condition2, condition3)).start();
        new Thread(() -> solution1b.print("c", 2, condition3, condition1)).start();
    }
}
