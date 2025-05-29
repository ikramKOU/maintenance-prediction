package ocp.maintenance.prediction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocp.maintenance.prediction.model.Employee;

@RestController
@RequestMapping()
public class EmployeeController {

    @Autowired
    // private EmplyeeService employeService;


    
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