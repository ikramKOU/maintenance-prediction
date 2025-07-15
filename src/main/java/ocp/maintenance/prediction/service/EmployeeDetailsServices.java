// package ocp.maintenance.prediction.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import ocp.maintenance.prediction.model.Employee;
// import ocp.maintenance.prediction.repository.EmployeRepository;

// @Service
// public class EmployeeDetailsServices implements UserDetailsService {

//     @Autowired
//     private EmployeRepository empRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         Employee emp = empRepository.findByUsername(username);
        
//         if (emp == null) {
//             throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
//         }

//         return User.withUsername(emp.getUsername())
//                 .password(emp.getPassword())
            
//                 .build();
//     }
// }

package ocp.maintenance.prediction.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ocp.maintenance.prediction.model.Employee;
import ocp.maintenance.prediction.repository.EmployeRepository;

@Service
@Transactional
public class EmployeeDetailsServices implements UserDetailsService, UserDetailsPasswordService {

    private final EmployeRepository empRepository;

    @Autowired
    public EmployeeDetailsServices(EmployeRepository empRepository) {
        this.empRepository = empRepository;
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     Employee emp = empRepository.findByUsername(username);
        
    //     if (emp == null) {
    //         throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
    //     }

    //     List<GrantedAuthority> authorities = Collections.singletonList(
    //         new SimpleGrantedAuthority("ROLE_" + emp.getRole())
    //     );

    //     return User.withUsername(emp.getUsername())
    //             .password(emp.getPassword())
    //             .authorities(authorities)
    //             .accountExpired(false)
    //             .accountLocked(false)
    //             .credentialsExpired(false)
    //             .disabled(false)
    //             .build();
    // }
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Employee emp = empRepository.findByUsername(username);
    
    if (emp == null) {
        throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
    }

    List<GrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_" + emp.getRole().name())
    );

    return User.withUsername(emp.getUsername())
            .password(emp.getPassword())
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
}


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Employee emp = empRepository.findByUsername(user.getUsername());
        if (emp != null) {
            emp.setPassword(newPassword);
            empRepository.save(emp);
        }
        return loadUserByUsername(user.getUsername());
    }
}