package top.lijingyuan.springboot.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.lijingyuan.springboot.test.model.EmployeeListVO;
import top.lijingyuan.springboot.test.model.EmployeeVO;

import java.net.URI;

@RestController
public class EmployeeController {

    private static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping(value = "/employees",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public EmployeeListVO getAllEmployees(
            @RequestHeader(name = "X-COM-PERSIST") String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", defaultValue = "ASIA") String headerLocation) {
        LOGGER.info("Header X-COM-PERSIST :: " + headerPersist);
        LOGGER.info("Header X-COM-LOCATION :: " + headerLocation);

        EmployeeListVO employees = getEmployeeList();
        return employees;
    }

    @GetMapping(value = "/employees/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EmployeeVO> getEmployeeById(@PathVariable("id") Integer id) {
        LOGGER.info("Requested employee id :: " + id);

        if (id != null && id > 0) {
            EmployeeVO employee = new EmployeeVO(id, "Lokesh", "Gupta", "howtodoinjava@gmail.com");
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/employees")
    public ResponseEntity<String> createEmployee(EmployeeVO employee) {
        employee.setId(111);

        //Build URI
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable("id") int id
            , EmployeeVO employee) {
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping(value = "/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private EmployeeListVO getEmployeeList() {
        EmployeeListVO employees = new EmployeeListVO();

        EmployeeVO empOne = new EmployeeVO(1, "Lokesh", "Gupta", "howtodoinjava@gmail.com");
        EmployeeVO empTwo = new EmployeeVO(2, "Amit", "Singhal", "asinghal@yahoo.com");
        EmployeeVO empThree = new EmployeeVO(3, "Kirti", "Mishra", "kmishra@gmail.com");

        employees.getEmployees().add(empOne);
        employees.getEmployees().add(empTwo);
        employees.getEmployees().add(empThree);

        return employees;
    }
}