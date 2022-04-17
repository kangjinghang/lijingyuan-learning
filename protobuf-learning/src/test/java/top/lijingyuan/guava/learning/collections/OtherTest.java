package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.*;
import org.junit.Test;

public class OtherTest {

    @Test
    public void rangeSetTest() {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        System.out.println("rangeSet:" + rangeSet);
        rangeSet.add(Range.closedOpen(11, 15));
        System.out.println("rangeSet:" + rangeSet);
        rangeSet.add(Range.open(15, 20));
        System.out.println("rangeSet:" + rangeSet);
        rangeSet.add(Range.openClosed(0, 0));
        System.out.println("rangeSet:" + rangeSet);
        rangeSet.remove(Range.open(5, 10));
        System.out.println("rangeSet:" + rangeSet);
    }

    @Test
    public void rangeMapTest() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo");
        System.out.println("rangeMap:" + rangeMap);
        rangeMap.put(Range.open(3, 6), "bar");
        System.out.println("rangeMap:" + rangeMap);
        rangeMap.put(Range.open(10, 20), "foo");
        System.out.println("rangeMap:" + rangeMap);
        rangeMap.remove(Range.closed(5, 11));
        System.out.println("rangeMap:" + rangeMap);
    }

    @Test
    public void classToInstanceMapTest() {
        ClassToInstanceMap<String> classToInstanceMapString = MutableClassToInstanceMap.create();
        ClassToInstanceMap<Person> classToInstanceMap = MutableClassToInstanceMap.create();
        Person person = new Person("peida", 20);
        System.out.println("person name :" + person.name + " age:" + person.age);
        classToInstanceMapString.put(String.class, "peida");
        System.out.println("string:" + classToInstanceMapString.getInstance(String.class));

        classToInstanceMap.putInstance(Person.class, person);
        Person person1 = classToInstanceMap.getInstance(Person.class);
        System.out.println("person1 name :" + person1.name + " age:" + person1.age);
    }
}

class Person {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}