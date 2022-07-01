package com.yangnk.others.myTest.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yangningkai
 * @create 2022-06-29 17:32
 **/

public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //获取Class对象，newInstance对象，打印出来
        //反射是可以根据字节码生成对象，并对其操作的技术
        Class<User> aClass = (Class<User>) Class.forName("com.yangnk.others.myTest.reflect.User");
        User user = aClass.newInstance();
//        System.out.printf(user.toString());
//        System.out.printf("=====user.toString():%s=====\n",user.toString());
//        Field[] fields = aClass.getDeclaredFields();
//        for (Field item : fields) {
//            System.out.printf("item.getName()::%s, item.getType()::%s, item.getModifiers()::%s,\n",
//                    item.toString(), item.getType(), item.getModifiers());
//        }

        Method[] methods = aClass.getDeclaredMethods();
        for (Method item : methods) {
//            TypeVariable<Method>[] typeParameters = item.getTypeParameters();
            Class<?>[] parameterTypes = item.getParameterTypes();
//            System.out.printf("item:%s\n", item.toString());

            for (Class<?> item1 : parameterTypes) {
//                System.out.printf("item1.getName():%s\n", item1.getName());
            }
//            System.out.printf("");
        }
        Object invoke = methods[2].invoke(user);
        System.out.printf(invoke.toString());

    }
}
