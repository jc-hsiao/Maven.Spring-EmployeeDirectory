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
    DepartmentService ds;

    @Autowired
    public EmployeeService( EmployeeRepository repository, DepartmentService ds){
        this.repository = repository;
        this.ds = ds;
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

    public Employee setDepartment(int employeeId, int departmentId){
        Optional<Employee> e = repository.findById(employeeId);
        return e.map( emp -> {
            emp.setDepartment(ds.findById(departmentId));
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

    public List<Employee> getEmployeesByManager(int managerId){
        return repository.findEmployeesByManagerId( managerId );
    }

    public List<Employee> getEmployeesWithoutManager(){
        return repository.findEmployeesByManagerIsNull();
    }

    public List<Employee> getEmployeesByDepartmentId(int deptId){
        return repository.findEmployeesByDepartmentId( deptId );
    }

    public int removeEmployeesByDept(int deptId){
        return repository.removeEmployeeByDepartmentId(deptId);
    }

    public Employee findManagerByEmpId( int employeeId ){
        return  findById(employeeId).getManager();
    }

    public List<Employee> getManagerHierarchy ( int employeeId ) {
        List<Employee> l = new ArrayList<>();
        Employee manager = findManagerByEmpId(employeeId);
        while(manager != null){
            l.add(manager);
            manager = findManagerByEmpId(manager.getId());
        }
        return l;
    }

    public List<Employee> getAllReportEmp(int managerId ){
        List<Employee> result = new ArrayList<>(getEmployeesByManager(managerId));
        for (int i = 0; i < result.size(); i++) {
            result.addAll(getAllReportEmp(result.get(i).getId()));
        }
        return result;
    }

    public int removeAllEmployeesByManagerId(int managerId){
        List<Employee> list = getAllReportEmp(managerId);
        return removeMultipleEmployees(list);
    }

    public int removeDirectReportId(int managerId){
        List<Employee> list = getEmployeesByManager(managerId);
        List<Employee> listOfBottomEmp = new ArrayList<>();
        for(Employee e :list){
            listOfBottomEmp.addAll( getEmployeesByManager(e.getId()) );
        }
        for(Employee e :listOfBottomEmp){
            setManager( e.getId(), findById(managerId) );
        }
        return removeMultipleEmployees(list);
    }

    public int mergeDept(int d1Id, int d2Id){
        Department d1 = ds.findById(d1Id);
        Department d2 = ds.findById(d2Id);
        setManager(d2.getManager().getId(),d1.getManager());
        List<Employee> l = getEmployeesByDepartmentId(d2.getId());
        for(Employee e:l){
            e.setDepartment(d1);
            update(e.getId(),e);
        }
        ds.delete(d2.getId());
        return l.size();
    }


}
