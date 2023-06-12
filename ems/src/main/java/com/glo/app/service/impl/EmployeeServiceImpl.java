package com.glo.app.service.impl;

import com.glo.app.entity.Employee;
import com.glo.app.exception.ResourceNotFoundException;
import com.glo.app.payload.EmployeeDto;
import com.glo.app.payload.EmployeeResponse;
import com.glo.app.repository.EmployeeRepository;
import com.glo.app.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    //Constructor based dependency injection
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = mapToEntity(employeeDto);
        Employee createdEmployee = employeeRepository.save(employee);
        return mapToDto(createdEmployee);
    }

    @Override
    public EmployeeResponse getEmployees(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> employees = employeeRepository.findAll(pageable);

        List<EmployeeDto> content = employees.stream().map(e -> mapToDto(e)).collect(Collectors.toList());

        //sending the response using the EmployeeResponse class object to the client
        //with pagination and sorting output
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setContent(content);
        employeeResponse.setPageNo(employees.getNumber());
        employeeResponse.setPageSize(employees.getSize());
        employeeResponse.setTotalElements(employees.getTotalElements());
        employeeResponse.setTotalPages(employees.getTotalPages());
        employeeResponse.setLast((employees.isLast()));

        return employeeResponse;
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        Employee employee = findByIdEmployee(id);
        return mapToDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(long id, EmployeeDto employeeDto) {
        Employee employee = findByIdEmployee(id);

        //initializing the field to update them
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setPosition(employeeDto.getPosition());
        employee.setSalary(employeeDto.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        Employee employee = findByIdEmployee(id);
        employeeRepository.delete(employee);
    }

    //to get the employee using valid id or else throw the exception back to the client
    private Employee findByIdEmployee(long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
        );
        return employee;
    }

    //Mapping to entity using model mapper library
    private Employee mapToEntity(EmployeeDto employeeDto){
        return modelMapper.map(employeeDto,Employee.class);
    }

    //Mapping to Dto using model mapper library
    private EmployeeDto mapToDto(Employee employee){
        return modelMapper.map(employee,EmployeeDto.class);
    }
}
