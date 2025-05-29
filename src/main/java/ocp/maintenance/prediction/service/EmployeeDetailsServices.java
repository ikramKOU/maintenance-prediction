package ocp.maintenance.prediction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.repository.EmployeRepository;

@Service
public class EmployeeDetailsServices implements UserDetailsService {

    @Autowired
    private EmployeRepository empRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee emp = empRepository.findByUsername(username);
        
        if (emp == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }

        return User.withUsername(emp.getUsername())
                .password(emp.getPassword())
            
                .build();
    }
}
