package com.yangnk.rpc.myTest.objectStream;

import java.io.*;

/**
 * @author yangningkai
 * @create 2022-06-30 17:29
 **/

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person = new Person("tom", 18);
        PersonOne personOne = new PersonOne("tomOne", 181);
        Person person1 = outObject(person, personOne);
//        PersonOne person1One = outObjectOne(personOne);
//        System.out.println(person1.toString());
        Person person2 = inObject();
//        System.out.println(person2.toString());

    }


    static Person outObject(Person person, PersonOne personOne) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/Users/yangnk/IdeaProjects/rpc/src/main/java/com/yangnk/rpc/myTest/objectStream/test"));
        objectOutputStream.writeObject(person);
        objectOutputStream.writeObject(personOne);
        objectOutputStream.flush();
        objectOutputStream.close();

        return person;
    }

//    static PersonOne outObjectOne(PersonOne person) throws IOException {
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/Users/yangnk/IdeaProjects/rpc/src/main/java/com/yangnk/rpc/myTest/objectStream/test"));
//        objectOutputStream.writeObject(person);
//        objectOutputStream.flush();
//        objectOutputStream.close();
//        return person;
//    }

    static Person inObject() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/Users/yangnk/IdeaProjects/rpc/src/main/java/com/yangnk/rpc/myTest/objectStream/test"));
        Person person = (Person)objectInputStream.readObject();
        PersonOne personOne = (PersonOne)objectInputStream.readObject();
        System.out.println(person.toString());
        System.out.println(personOne.toString());
        objectInputStream.close();
        return person;


//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/Users/yangnk/IdeaProjects/rpc/src/main/java/com/yangnk/rpc/myTest/objectStream/test"));
//        Person person = new Person("tom", 18);
//        objectOutputStream.writeObject(person);
//        return person;
    }

}
