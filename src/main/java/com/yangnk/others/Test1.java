package com.yangnk.others;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangningkai
 * @create 2019-01-10 16:27
 **/

public class Test1 {
    public static void main(String[] args) {
        Student student1 = new Student();
        List list = new ArrayList();

        if (student1 instanceof Student) {
            System.out.println("111");
        }else {
            System.out.println("222");
        }
    }

    private static class Student {
        private String name;
    }
}
