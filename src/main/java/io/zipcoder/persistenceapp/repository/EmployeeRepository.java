package io.zipcoder.persistenceapp.repository;
import io.zipcoder.persistenceapp.model.Department;
import io.zipcoder.persistenceapp.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {

    List<Employee> findEmployeesByManager(Employee manager);
    List<Employee> findEmployeesByManagerIsNull();
    List<Employee> findEmployeesByDepartment(Department department);
    boolean removeEmployeeById(int id);
    int removeEmployeeByDepartment( Department department);
}