package com.yangnk.others.ths;

/**
 * @author yangningkai
 * @create 2022-06-29 22:49
 **/

public class Solution1a1 {
    Object lock = new Object();
    int num = 0;
    int letter = 0;

    void print(int type) {
        synchronized (lock) {
            for (int i = 0; i < 26; i++) {
                if (type == 0) {//数字
                    System.out.print(i + 1);
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (type == 1) {
                    System.out.print((char) ('a' + i));
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution1a1 solution1a1 = new Solution1a1();
        new Thread(() -> solution1a1.print(0)).start();
        new Thread(() -> solution1a1.print(1)).start();

    }
}
