package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.model.Department;
import io.zipcoder.persistenceapp.model.Employee;
import io.zipcoder.persistenceapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    EmployeeRepository repository;

    @Autowired
    public EmployeeService( EmployeeRepository repository ){
        this.repository = repository;
    }

    public Employee findById(int id){
        return repository.findById(id).orElse(null);
    }

    public Iterable<Employee> findAll(){
        return repository.findAll();
    }

    public Employee create(Employee e){
        return repository.save(e);
    }


    public Employee setManager(int employeeId, Employee manager){
        Optional<Employee> e = repository.findById(employeeId);
        return e.map( emp -> {
            emp.setManager(manager);
            return update(employeeId, emp);
        }).orElse(null);
    }


    public Employee update(int employeeId, Employee info){
        Optional<Employee> e = repository.findById(employeeId);
        return e.map(employee -> {
            info.setId(employeeId);
            repository.save(info);
            return info;
        }).orElse(null);
    }

    public boolean removeEmployeeById(int employeeId){
        return repository.removeEmployeeById(employeeId);
    }

    private int removeMultipleEmployees(List<Employee> list){
        repository.deleteAll(list);
        return list.size();
    }

//    public boolean delete(int employeeId){
//        Optional<Employee> e = repository.findById(employeeId);
//        return e.map( emp -> {
//            repository.delete(emp);
//            return true;
//        }).orElse(false);
//    }

    public List<Employee> getEmployeesByManager(Employee manager){
        return repository.findEmployeesByManager( manager );
    }

    public List<Employee> getEmployeesWithoutManager(){
        return repository.findEmployeesByManagerIsNull();
    }

    public List<Employee> getEmployeeByDepartment(Department department){
        return repository.findEmployeesByDepartment( department );
    }

    public int removeEmployeesByDept(Department department){
        return repository.removeEmployeeByDepartment(department);
    }

    public Employee findManagerByEmp( Employee employee ){
        return  employee.getManager();
    }

    public List<Employee> getManagerHierarchy ( Employee employee ) {
        List<Employee> l = new ArrayList<>();
        Employee manager = findManagerByEmp(employee);
        while(manager != null){
            l.add(manager);
            manager = findManagerByEmp(manager);
        }
        return l;
    }

    public List<Employee> getAllReportEmp( Employee manager ){
        List<Employee> result = new ArrayList<>(getEmployeesByManager(manager));
        for (int i = 0; i < result.size(); i++) {
            result.addAll(getAllReportEmp(result.get(i)));
        }
        return result;
    }

    public int removeAllEmployeesByManager(Employee manager){
        List<Employee> list = getAllReportEmp(manager);
        return removeMultipleEmployees(list);
    }

    public int removeDirectReport(Employee manager){
        List<Employee> list = getEmployeesByManager(manager);
        List<Employee> listOfBottomEmp = new ArrayList<>();
        for(Employee e :list){
            listOfBottomEmp.addAll( getEmployeesByManager(e) );
        }
        for(Employee e :listOfBottomEmp){
            setManager( e.getId(), manager );
        }
        return removeMultipleEmployees(list);
    }


}
