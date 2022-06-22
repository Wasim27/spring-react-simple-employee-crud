package io.employee.employeecrud.employee;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

  private final EmployeeRepository employeeRepository;

  public EmployeesController(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @GetMapping("/{id}")
  public Employee getEmployee(@PathVariable Long id) {
    return employeeRepository.findById(id).orElseThrow(RuntimeException::new);
  }

  @PostMapping
  public ResponseEntity addEmployee(@RequestBody Employee employee) throws URISyntaxException {
    Employee savedEmployee = employeeRepository.save(employee);
    return ResponseEntity.created(new URI("/employees/" + savedEmployee.getId())).body(savedEmployee);
  }

  @PostMapping("/{id}")
  public ResponseEntity updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
    Employee currentEmployee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);
    currentEmployee.setName(employee.getName());
    currentEmployee.setEmail(employee.getEmail());
    currentEmployee = employeeRepository.save(employee);

    return ResponseEntity.ok(employee);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteClient(@PathVariable Long id) {
    employeeRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
