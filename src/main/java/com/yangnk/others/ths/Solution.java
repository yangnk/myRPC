package com.yangnk.others.ths;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangningkai
 * @create 2022-06-29 19:43
 **/

public class Solution {
    public static void main(String[] args) {
        divide(10);
    }


    public static String divide(int a) {
        int ans = a;
        List<Integer> list = new ArrayList<>();
        boolean flag = false;

        while (true) {
            for (int i = 2; i <= ans / 2; i++) {
                if (ans % i == 0) {
                    list.add(i);
                    ans = ans / i;
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                list.add(ans);
                break;
            }
        }
        System.out.printf(list.toString());
        return null;
    }
}
