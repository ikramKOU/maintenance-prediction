package ocp.maintenance.prediction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.service.EmplyeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    
    @Autowired
    private EmplyeeService employeeService;


  @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updated = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }




    
    @GetMapping("/")
    public String Home(){
        return "heeeeeeeeeeeeey youuu my name is mow ";
    }
    // @PostMapping
    // public ResponseEntity<Employee> createEmploye(@RequestBody Employee employe) {
    //     return new ResponseEntity<>(employeService.createEmployee(employe), HttpStatus.CREATED);
    // }

    // @GetMapping
    // public ResponseEntity<List<Employee>> getAllEmployes() {
    //     return ResponseEntity.ok(employeService.getAllEmployes());
    // }

    // @GetMapping("/role/{role}")
    // public ResponseEntity<List<Employee>> getEmployesByRole(@PathVariable RoleEmployee role) {
    //     return ResponseEntity.ok(employeService.getEmployesByRole(role));
    // }

    // @GetMapping("/{id}/alertes")
    // public ResponseEntity<List<Alerte>> getAlertesByEmploye(@PathVariable Long id) {
    //     return ResponseEntity.ok(employeService.getAlertesByEmploye(id));
    // }

}