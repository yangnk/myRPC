package com.yangnk.others.myTest.objectStream;

import java.io.Serializable;

/**
 * @author yangningkai
 * @create 2022-06-30 17:28
 **/

public class Person implements Serializable {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" + "nameOne='" + name + '\'' + ", ageOne=" + age + '}';
    }
}
