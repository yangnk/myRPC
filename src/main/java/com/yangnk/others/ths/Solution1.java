package com.yangnk.others.ths;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程同步，多线程顺序打印
 * @author yangningkai
 * @create 2022-06-29 19:57
 **/

//利用lock实现同步，多线程执行的任务相同，根据state判断执行哪一个，类似乐观锁
public class Solution1 {
    Lock lock = new ReentrantLock();
    volatile int state = 0;
    volatile int time = 0;

    public Solution1(int time) {
        this.time = time;
    }

    public void print(String context, int num) {
        while (state < time) {
            lock.lock();
//        for (int i = 0; i < time; ) {
            if (state % 3 == num) {
                System.out.println(context);
                System.out.printf("state:%d\n", state);
                System.out.printf("Thread.currentThread().getName():%s\n", Thread.currentThread().getName());
                state++;
//                i++;
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1(3);
        new Thread(() -> solution1.print("a", 0), "a").start();
        new Thread(() -> solution1.print("b", 1), "b").start();
        new Thread(() -> solution1.print("c", 2), "c").start();
    }







//    public static void main(String[] args) {
//        boolean flag1 = true;
//        boolean flag2 = false;
//        boolean flag3 = false;
//        boolean flag4 = false;
//
//        Thread t1 = new Thread(){
//            @Override
//            public void run() {
//                while (true) {
//                    if (flag1 == true) {
//                        System.out.println("a");
//                        flag1 = false;
//                        flag2 = true;
//                    }
//                }
//            }
//        };
//
//        Thread t2 = new Thread(){
//            @Override
//            public void run() {
//                while (true) {
//                    if (flag2 == true) {
//                        System.out.println("b");
//                        flag2 = false;
//                        flag3 = true;
//                    }
//                }
//            }
//        };
//
//        Thread t3 = new Thread(){
//            @Override
//            public void run() {
//                while (true) {
//                    if (flag3 == true) {
//                        System.out.println("c");
//                        flag3 = false;
//                        flag4 = true;
//                    }
//                }
//            }
//        };
//
//        Thread t4 = new Thread(){
//            @Override
//            public void run() {
//                while (true) {
//                    if (flag4 == true) {
//                        System.out.println("c");
//                        flag4 = false;
//                        flag1 = true;
//                    }
//                }
//            }
//        };
//
//
//        while (true) {
//            t1.start();
//            t2.start();
//            t3.start();
//            t4.start();
//        }
//    }


}
