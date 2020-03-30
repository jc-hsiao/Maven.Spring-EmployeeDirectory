package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.model.Department;
import io.zipcoder.persistenceapp.model.Employee;
import io.zipcoder.persistenceapp.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {
    DepartmentRepository repository;
    EmployeeService es;

    @Autowired
    public DepartmentService( DepartmentRepository repository, EmployeeService es){
        this.repository = repository;
        this.es = es;
    }

    public Department findById(int id){
        return repository.findById(id).orElse(null);
    }

    public Iterable<Department> findAll(){
        return repository.findAll();
    }

    public Department create(Department d){
        return repository.save(d);
    }

    public Department update(int departmentId, Department info){
        Optional<Department> d = repository.findById(departmentId);
        return d.map(department -> {
            info.setId(departmentId);
            repository.save(info);
            return info;
        }).orElse(null);
    }

    public boolean delete(int departmentId){
        Optional<Department> e = repository.findById(departmentId);
        return e.map( emp -> {
            repository.delete(emp);
            return true;
        }).orElse(false);
    }


    public Department setManager(int departmentId, int managerId){
        Optional<Department> p = repository.findById(departmentId);
        Employee manager = es.findById(managerId);
        return p.map(department -> {
            department.setManager(manager);
            repository.save(department);
            return department;
        }).orElse(null);
    }

    public Department changeName(int departmentId, String newName){
        Optional<Department> p = repository.findById(departmentId);
        return p.map(department -> {
            department.setName(newName);
            repository.save(department);
            return department;
        }).orElse(null);
    }


}
