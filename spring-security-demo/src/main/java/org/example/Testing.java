package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Person class - id  and name. create objects and add to the list. convert list to hash map using java streams api.
 * Key - id
 * Value - name / Person object
 * Map<Integer,String> or Map<Integer,Person>
 *
 */
public class Testing {
    public static void main(String[] args) {

        Person p1 = new Person(1,"p1");
        Person p2 = new Person(2,"p2");
        Person p3 = new Person(3,"p3");
        Person p4 = new Person(4,"p4");
        List<Person> personList = Arrays.asList(p1,p2,p3,p4);

        Map<Integer,Person> personMap = personList.stream().collect(Collectors.toMap(x-> x.id, x-> x));
        System.out.println(personMap);

    }
}

class Person{
    int id;
    String name;
    Person(int id, String name){
        this.id=id;
        this.name=name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
