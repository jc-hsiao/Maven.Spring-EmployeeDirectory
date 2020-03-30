package io.zipcoder.persistenceapp.model;

import javax.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    int id;
    String name;

    @OneToOne
    Employee Manager;

    public Department(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return Manager;
    }

    public void setManager(Employee manager) {
        Manager = manager;
    }
}
