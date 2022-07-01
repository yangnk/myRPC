package com.yangnk.rpc.myTest.objectStream;

import java.io.Serializable;

/**
 * @author yangningkai
 * @create 2022-06-30 17:28
 **/

public class PersonOne implements Serializable {
    String nameOne;
    int ageOne;

    public PersonOne(String name, int age) {
        this.nameOne = name;
        this.ageOne = age;
    }

    @Override
    public String toString() {
        return "Person{" + "nameOne='" + nameOne + '\'' + ", ageOne=" + ageOne + '}';
    }
}
