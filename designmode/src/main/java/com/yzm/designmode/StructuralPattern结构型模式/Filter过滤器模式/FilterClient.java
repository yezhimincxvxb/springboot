package com.yzm.designmode.StructuralPattern结构型模式.Filter过滤器模式;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 过滤器模式（Filter Pattern）或标准模式（Criteria Pattern）是一种设计模式，这种模式允许开发人员使用不同的标准来过滤一组对象，
 * 通过逻辑运算以解耦的方式把它们连接起来。这种类型的设计模式属于结构型模式，它结合多个标准来获得单一标准。
 */
public class FilterClient {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Robert", "Male", "Single"));
        persons.add(new Person("John", "Male", "Married"));
        persons.add(new Person("Laura", "Female", "Married"));
        persons.add(new Person("Diana", "Female", "Single"));
        persons.add(new Person("Mike", "Male", "Single"));
        persons.add(new Person("Bobby", "Male", "Single"));
        persons.add(new Person("Lucy", "Female", "Married"));
        persons.add(new Person("David", "Male", "Married"));

//        test01(persons);

        //JDK8 分组
        Map<String, List<Person>> collect = persons.stream().collect(Collectors.groupingBy(Person::getGender));
        collect.forEach((x, y) -> {
            System.out.println(x);
            y.forEach(System.out::println);
        });

        //过滤
        System.out.println("已婚男士：");
        List<Person> marriedMale = persons.stream()
                .filter(person -> person.getGender().equalsIgnoreCase(Criteria.MALE))
                .filter(person -> person.getMaritalStatus().equalsIgnoreCase(Criteria.MARRIED))
                .collect(Collectors.toList());
        marriedMale.forEach(System.out::println);
    }

    private static void test01(List<Person> persons) {
        //男性
        System.out.println("男性：");
        Criteria male = new CriteriaMale();
        male.meetCriteria(persons).forEach(System.out::println);
        //女性
        System.out.println("女性：");
        Criteria female = new CriteriaFemale();
        female.meetCriteria(persons).forEach(System.out::println);
        //单身
        System.out.println("单身：");
        Criteria single = new CriteriaSingle();
        single.meetCriteria(persons).forEach(System.out::println);
        //已婚
        System.out.println("已婚：");
        Criteria married = new CriteriaMarital();
        married.meetCriteria(persons).forEach(System.out::println);
        //男性单身
        System.out.println("男性单身：");
        Criteria singleMale = new AndCriteria(male, single);
        singleMale.meetCriteria(persons).forEach(System.out::println);
        //女性已婚
        System.out.println("女性已婚：");
        Criteria marriedOrFemale = new AndCriteria(female, married);
        marriedOrFemale.meetCriteria(persons).forEach(System.out::println);

        //男性单身或女性已婚
        System.out.println("男性单身或女性：");
        Criteria singleMaleOrFemale = new OrCriteria(singleMale, marriedOrFemale);
        singleMaleOrFemale.meetCriteria(persons).forEach(System.out::println);
    }
}

/**
 * 实体类
 */
class Person {
    private String name;
    private String gender; //性别
    private String maritalStatus; //婚姻状态

    public Person(String name, String gender, String maritalStatus) {
        this.name = name;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}

/**
 * 符合标准的进行分组、过滤
 */
interface Criteria {
    public static final String MALE = "MALE";
    public static final String FEMALE = "FEMALE";
    public static final String SINGLE = "SINGLE";
    public static final String MARRIED = "MARRIED";

    List<Person> meetCriteria(List<Person> persons);
}

class CriteriaMale implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> malePersons = new ArrayList<>();
        for (Person person : persons) {
            if (person.getGender().equalsIgnoreCase(MALE)) {
                malePersons.add(person);
            }
        }
        return malePersons;
    }
}

class CriteriaFemale implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> femalePersons = new ArrayList<>();
        for (Person person : persons) {
            if (person.getGender().equalsIgnoreCase(FEMALE)) {
                femalePersons.add(person);
            }
        }
        return femalePersons;
    }
}

class CriteriaSingle implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> singlePersons = new ArrayList<>();
        for (Person person : persons) {
            if (person.getMaritalStatus().equalsIgnoreCase(SINGLE)) {
                singlePersons.add(person);
            }
        }
        return singlePersons;
    }
}

class CriteriaMarital implements Criteria {

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> singlePersons = new ArrayList<>();
        for (Person person : persons) {
            if (person.getMaritalStatus().equalsIgnoreCase(MARRIED)) {
                singlePersons.add(person);
            }
        }
        return singlePersons;
    }
}

class AndCriteria implements Criteria {

    private final Criteria criteria;
    private final Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> firstCriteriaPersons = criteria.meetCriteria(persons);
        return otherCriteria.meetCriteria(firstCriteriaPersons);
    }
}

class OrCriteria implements Criteria {

    private final Criteria criteria;
    private final Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> firstCriteriaItems = criteria.meetCriteria(persons);
        List<Person> otherCriteriaItems = otherCriteria.meetCriteria(persons);

        for (Person person : otherCriteriaItems) {
            if (!firstCriteriaItems.contains(person)) {
                firstCriteriaItems.add(person);
            }
        }
        return firstCriteriaItems;
    }
}