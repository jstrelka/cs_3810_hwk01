/**
 * CS3810 - Principles Database Systems - Fall 2019
 * An employee with id, name, and department
 * @author Thyago Mota
 * @date Aug-16-2018
 */

// Employee Class
public class Employee {

    // Employee member variables
    private int    id;
    private String name;
    private String department;

    // Employee Constructor
    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    // Employee ID Getter/Setters
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Employee NAME Getter/Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Employee DEPARTMENT Getter/Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Employee toString() for .csv output
    @Override
    public String toString() {
        return id + "," + name + "," + department;
    }
}
