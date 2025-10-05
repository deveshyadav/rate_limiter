package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Find a pair with the given sum in an array
 * Input:
 * nums = [8, 7, 2, 5, 3, 1]
 * target = 10
 * Output:
 * Pair found (8, 2)
 * Pair found (7, 3)
 */
public class Testing2 implements MyInterface2,MyInterface{
    public void existingMethod(String str){
        System.out.println("String is: "+str);
    }


    @Override
    public void newMethod() {
        MyInterface2.super.newMethod();
    }

    public void disp(String str){
        System.out.println("String is: "+str);
    }

    public static void main(String[] args) {
        MyInterface obj = new Testing2();
        obj.newMethod();
    }


}

interface MyInterface{
     static void newMethod(){
        System.out.println("Newly added default method 1");
    }

    void existingMethod(String str);
}

interface MyInterface2{

    default void newMethod(){
        System.out.println("Newly added default method 2");
    }

    void disp(String str);
}






