package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.model.Employee;
import io.zipcoder.persistenceapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<Employee> find(@PathVariable int id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping( "/all" )
    public ResponseEntity<Iterable<Employee>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping( "/create" )
    ResponseEntity<Employee> create(@RequestBody Employee e){
        return new ResponseEntity<>(service.create(e), HttpStatus.CREATED );
    }

    @PutMapping( "/setManager/{id}" )
    ResponseEntity<Employee> setManager(@PathVariable int id, @RequestBody Employee manager){
        return new ResponseEntity<>(service.setManager(id, manager), HttpStatus.CREATED );
    }

    @PutMapping( "/setDepartment/{id}/{deptId}" )
    ResponseEntity<Employee> setManager(@PathVariable int id, @PathVariable int deptId){
        return new ResponseEntity<>(service.setDepartment(id, deptId), HttpStatus.CREATED );
    }

    @PutMapping( "/{id}" )
    ResponseEntity<Employee> update(@PathVariable int id, @RequestBody Employee info){
        return new ResponseEntity<>(service.update(id, info), HttpStatus.OK );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Boolean> remove(@PathVariable int id) {
        return new ResponseEntity<>(service.removeEmployeeById(id),HttpStatus.OK);
    }

    @GetMapping( "/byManager/{id}" )
    public ResponseEntity<Iterable<Employee>> getByManager(@PathVariable int id) {
        return new ResponseEntity<>(service.getEmployeesByManager(id), HttpStatus.OK);
    }

    @GetMapping( "/noManager" )
    public ResponseEntity<Iterable<Employee>> getByNoManager() {
        return new ResponseEntity<>(service.getEmployeesWithoutManager(), HttpStatus.OK);
    }

    @GetMapping( "/byDepartment/{id}" )
    public ResponseEntity<Iterable<Employee>> getByDepartment(@PathVariable int id) {
        return new ResponseEntity<>(service.getEmployeesByDepartmentId(id), HttpStatus.OK);
    }

    @DeleteMapping( "/byDepartment/{id}" )
    public ResponseEntity<Integer> removeByDept(@PathVariable int id) {
        return new ResponseEntity<>(service.removeEmployeesByDept(id),HttpStatus.OK);
    }

    @GetMapping( "/manager" )
    public ResponseEntity<Employee> getManager(@PathVariable int id) {
        return new ResponseEntity<>(service.findManagerByEmpId(id), HttpStatus.OK);
    }

    @GetMapping( "/managerHierarchy/{id}" )
    public ResponseEntity<Iterable<Employee>> getManagerHierarchy(@PathVariable int id) {
        return new ResponseEntity<>(service.getManagerHierarchy(id), HttpStatus.OK);
    }

    @GetMapping( "/allReport/{id}" )
    public ResponseEntity<Iterable<Employee>> getAllReportEmp(@PathVariable int id) {
        return new ResponseEntity<>(service.getAllReportEmp(id), HttpStatus.OK);
    }

    @DeleteMapping( "/byManager/{id}" )
    public ResponseEntity<Integer> removeAllEmployeesByManagerId(@PathVariable int id) {
        return new ResponseEntity<>(service.removeAllEmployeesByManagerId(id),HttpStatus.OK);
    }

    @DeleteMapping( "/byManagerDirect/{id}" )
    public ResponseEntity<Integer> removeDirectReportId(@PathVariable int id) {
        return new ResponseEntity<>(service.removeDirectReportId(id),HttpStatus.OK);
    }

    @PutMapping("/mergeDepartment/{id2}/to/{id}")
    public ResponseEntity<Integer> remove(@PathVariable int id, @PathVariable int id2) {
        return new ResponseEntity<>(service.mergeDept(id,id2),HttpStatus.OK);
    }


}
