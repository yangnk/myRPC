package com.yangnk.others.myTest.reflect;

/**
 * @author yangningkai
 * @create 2022-06-29 17:31
 **/

public class User{
    // Field
    int no;

    // Constructor
    public User(){

    }
    public User(int no){
        this.no = no;
    }

    // Method
    public void setNo(int no){
        this.no = no;
    }
    public int getNo(){
        return no;
    }

    @Override
    public String toString() {
        return "User{" + "no=" + no + '}';
    }
}