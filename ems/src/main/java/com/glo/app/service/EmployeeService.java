package com.glo.app.service;

import com.glo.app.payload.EmployeeDto;
import com.glo.app.payload.EmployeeResponse;

public interface EmployeeService {
    //to create employee
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    //to get the list of employees
    EmployeeResponse getEmployees(int pageNo, int pageSize, String sortBy, String sortDir);
    //to get the employee using the valid id
    EmployeeDto getEmployeeById(long id);
    //to update the employee using the valid id
    EmployeeDto updateEmployee(long id, EmployeeDto employeeDto);
    // to delete the employee using valid id
    void deleteEmployee(long id);
}
