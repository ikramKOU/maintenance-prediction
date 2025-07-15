// package ocp.maintenance.prediction.service;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import ocp.maintenance.prediction.model.Employee;
// import ocp.maintenance.prediction.repository.EmployeRepository;


// @Service
// public class EmplyeeService {

//     @Autowired
//     private EmployeRepository employeRepository;



//     public Employee findByUsername(String username) {
//         return employeRepository.findByUsername(username);
//     }

//     public List<Employee> getAllEmployees() {
//         return employeRepository.findAll();
//     }

//     public Optional<Employee> getEmployeeById(Long id) {
//         return employeRepository.findById(id);
//     }

    

//     public Employee updateEmployee(Long id, Employee newData) {
//         return employeRepository.findById(id).map(emp -> {
//             emp.setNom(newData.getNom());
//             emp.setUsername(newData.getUsername());
//             emp.setPhone(newData.getPhone());
//             emp.setEmail(newData.getEmail());
//             emp.setRole(newData.getRole());
//             return employeRepository.save(emp);
//         }).orElseThrow(() -> new RuntimeException("Employee not found"));
//     }

//     public void deleteEmployee(Long id) {
//         employeRepository.deleteById(id);
//     }

//     public Object findById(Long employe) {
//         throw new UnsupportedOperationException("Not supported yet.");
//     }
    
// }

package ocp.maintenance.prediction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.repository.EmployeRepository;

@Service
@Transactional
public class EmployeeService {

    private final EmployeRepository employeRepository;

    @Autowired
    public EmployeeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    public Employee findByUsername(String username) {
        return employeRepository.findByUsername(username);
    }

    public List<Employee> getAllEmployees() {
        return employeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee newData) {
        return employeRepository.findById(id)
                .map(emp -> {
                    emp.setNom(newData.getNom());
                    emp.setUsername(newData.getUsername());
                    emp.setPhone(newData.getPhone());
                    emp.setEmail(newData.getEmail());
                    emp.setRole(newData.getRole());
                    if (newData.getPassword() != null && !newData.getPassword().isEmpty()) {
                        emp.setPassword(newData.getPassword());
                    }
                    return employeRepository.save(emp);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public void deleteEmployee(Long id) {
        employeRepository.deleteById(id);
    }

    public Optional<Employee> findById(Long id) {
        return employeRepository.findById(id);
    }
}