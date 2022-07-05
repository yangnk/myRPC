package com.yangnk.others.myTest.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangningkai
 * @create 2019-01-05 17:00
 **/

public class GenericTest1 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("abc");
        setList(list);
        list.add("111");
        setList(list);

        List<Integer> list1 = new ArrayList<>();
        list1.add(123);
        setList(list1);

    }

    static void setList(List<?> list) {
        System.out.println(list.toString());
    }
}
