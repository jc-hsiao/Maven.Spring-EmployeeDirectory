package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.model.Department;
import io.zipcoder.persistenceapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping( "/create" )
    ResponseEntity<Department> create(@RequestBody Department d){
        return new ResponseEntity<>(service.create(d), HttpStatus.CREATED );
    }

    @PutMapping( "/setManager" )
    ResponseEntity<Department> setManager(@PathVariable int deptId, @PathVariable int empId){
        return new ResponseEntity<>(service.setManager(deptId,empId), HttpStatus.CREATED );
    }

    @GetMapping( "/all" )
    public ResponseEntity<Iterable<Department>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Boolean> remove(@PathVariable int id) {
        return new ResponseEntity<>(service.delete(id),HttpStatus.OK);
    }

    @PutMapping( "/{id}" )
    ResponseEntity<Department> update(@PathVariable int id, @RequestBody Department info){
        return new ResponseEntity<>(service.update(id, info), HttpStatus.OK );
    }

    @PutMapping( "/{id}" )
    ResponseEntity<Department> changeName(@PathVariable int id, @PathVariable String newName){
        return new ResponseEntity<>(service.changeName(id, newName), HttpStatus.OK );
    }

}
