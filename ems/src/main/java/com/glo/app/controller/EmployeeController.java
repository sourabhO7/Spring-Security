package com.glo.app.controller;

import com.glo.app.payload.EmployeeDto;
import com.glo.app.payload.EmployeeResponse;
import com.glo.app.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Spring boot REST api for createEmployee
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto
    ){
        return new ResponseEntity(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }

    //Spring boot REST api for getEmployees
    @GetMapping
    public EmployeeResponse getEmployees(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        return employeeService.getEmployees(pageNo,pageSize,sortBy,sortDir);
    }

    //Spring boot REST api for getEmployeeById
    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable("id") long id){
        return employeeService.getEmployeeById(id);
    }

    //Spring boot REST api for updateEmployee
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeDto updateEmployee(
            @PathVariable("id") long id,
            @RequestBody EmployeeDto employeeDto
    ){
        return employeeService.updateEmployee(id,employeeDto);
    }

    //Spring boot REST api for deleteEmployee
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployee(@PathVariable("id") long id){
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }
}
