package in.sk.main.services;

import in.sk.main.entities.Employee;
import in.sk.main.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean employeeLogin(String email,String password){
        Employee employee=employeeRepository.getEmployeeByEmail(email);
        if(employee!=null){
            if(employee.getPassword().equals(password)) return true;
        }
        return false;
    }

    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public Page<Employee> getAllEmployeeByPageination(Pageable pageable){
        return employeeRepository.findAll(pageable);
    }

    public void addEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public Employee getEmployeeByEmail(String email){
        Employee employee=employeeRepository.getEmployeeByEmail(email);
        return employee;
    }


    public void updatedEmployeeByEmail(Employee employee){
        employeeRepository.save(employee);
    }

    public void deleteEmployeeByEmail(String email){
        Employee employee=employeeRepository.getEmployeeByEmail(email);
        if(employee!=null){
            employeeRepository.delete(employee);
        }else{
            throw new RuntimeException("Employee not found for this email : "+email);
        }
    }
}
