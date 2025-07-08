package ocp.maintenance.prediction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ocp.maintenance.prediction.model.Employee;

public interface EmployeRepository extends JpaRepository<Employee, Long> {
    // Optional<Employee> findFirstByRole(RoleEmploye role);
    public Employee findByUsername(String username);

    // Optional<Employee> findByUsername(String username);


    public Employee findByEmail(String username);




}
