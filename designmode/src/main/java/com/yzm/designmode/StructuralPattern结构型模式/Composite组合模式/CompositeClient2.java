package com.yzm.designmode.StructuralPattern结构型模式.Composite组合模式;

import java.util.ArrayList;
import java.util.List;

public class CompositeClient2 {
    public static void main(String[] args) {
        //CEO有销售和营销主管有2个下属
        Employee CEO = new Employee("John","CEO", 30000);
        Employee headMarketing = new Employee("Michel","Head Marketing", 20000);
        Employee headSales = new Employee("Robert","Head Sales", 20000);
        CEO.add(headSales);
        CEO.add(headMarketing);
        //营销主管有2个下属
        Employee clerk1 = new Employee("Laura","Marketing", 10000);
        Employee clerk2 = new Employee("Bob","Marketing", 10000);
        headMarketing.add(clerk1);
        headMarketing.add(clerk2);
        //销售主管有2个下属
        Employee salesExecutive1 = new Employee("Richard","Sales", 10000);
        Employee salesExecutive2 = new Employee("Rob","Sales", 10000);
        headSales.add(salesExecutive1);
        headSales.add(salesExecutive2);

        CEO.iterator();
    }
}

/**
 * 员工类，每个员工都有一个下属集合(包含其他员工)
 */
class Employee {
    private String name;
    private String dept;
    private int salary;
    private List<Employee> subordinates;

    public Employee(String name,String dept, int sal) {
        this.name = name;
        this.dept = dept;
        this.salary = sal;
        subordinates = new ArrayList<>();
    }

    public void add(Employee e) {
        subordinates.add(e);
    }

    public void remove(Employee e) {
        subordinates.remove(e);
    }

    public void iterator() {
        System.out.println(this);
        for (Employee employee : subordinates) {
            employee.iterator();
        }
    }

    public String toString(){
        return ("Employee :[ Name : "+ name
                +", dept : "+ dept + ", salary :"
                + salary+" ]");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<Employee> getSubordinates(){
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }
}