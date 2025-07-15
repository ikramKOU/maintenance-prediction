package ocp.maintenance.prediction.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ocp.maintenance.prediction.model.Employee;
import java.util.Collection;

public class EmployeeDetails implements UserDetails {

    private final Employee employee;
    private final Collection<? extends GrantedAuthority> authorities;

    public EmployeeDetails(Employee employee, Collection<? extends GrantedAuthority> authorities) {
        this.employee = employee;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Employee getEmployee() {
        return employee;
    }
}
