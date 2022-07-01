package com.yangnk.others.ths;

/**
 * 利用synchronized+wait()/notify()+state条件来实现进程中通信
 * @author yangningkai
 * @create 2022-06-29 22:17
 **/

public class Solution1a {
    Object lock = new Object();
    volatile int state;


    void print(String context, int num) {
        while (true) {
            synchronized (lock) {
                while (state % 3 != num) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(context);
                lock.notifyAll();
                state++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution1a solution1a = new Solution1a();
        new Thread(() -> solution1a.print("a", 0), "a").start();
        new Thread(() -> solution1a.print("b", 1), "b").start();
        new Thread(() -> solution1a.print("c", 2), "c").start();
    }
}
