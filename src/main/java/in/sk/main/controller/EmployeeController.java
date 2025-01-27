package in.sk.main.controller;

import in.sk.main.entities.Employee;
import in.sk.main.entities.EmployeeOrders;
import in.sk.main.entities.Inquiry;
import in.sk.main.entities.Orders;
import in.sk.main.repositories.EmployeeOrdersRepository;
import in.sk.main.services.CourseService;
import in.sk.main.services.EmployeeService;
import in.sk.main.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Controller
@SessionAttributes("sessionEmployee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private EmployeeOrdersRepository employeeOrdersRepository;

    private Employee sessionEmployee=null;

    @GetMapping("/employeeLogin")
    public String employeeLogin(Model model){
        model.addAttribute("employee",new Employee());
        return "employee-login";
    }
    @PostMapping("/employeeLoginForm")
    public String employeeLoginForm(@ModelAttribute("employee") Employee employee,Model model){
        boolean status=employeeService.employeeLogin(employee.getEmail(),employee.getPassword());
        if(status){
            Employee employeeProfile=employeeService.getEmployeeByEmail(employee.getEmail());
            model.addAttribute("sessionEmployee",employeeProfile);
            sessionEmployee=employeeProfile;
            return "employee-profile";
        }else{
            model.addAttribute("errorMsg","Incorrect Email id or Password");
            return "employee-login";
        }
    }
    @GetMapping("/employeeProfile")
    public String employeeProfile(){
        if(sessionEmployee==null) return "employee-login";
        else return "employee-profile";
    }
    @GetMapping("/employeeLogout")
    public String employeeLogout(SessionStatus sessionStatus){
        sessionEmployee=null;
        sessionStatus.setComplete();
        return "employee-login";
    }

    @GetMapping("/sellCourse")
    public String handleSellCourse(Model model){
        if(sessionEmployee==null) return "employee-login";
        else{
            List<String> courseNameList=courseService.getAllCourseName();
            model.addAttribute("courseNameList",courseNameList);

            String uuidOrderId= UUID.randomUUID().toString();
            model.addAttribute("uuidOrderId",uuidOrderId);

            model.addAttribute("orders", new Orders());
            return "sell-course";
        }
    }

    @PostMapping("/sellCourseForm")
    public String handleSellCourseForm(@ModelAttribute("orders") Orders order,@SessionAttribute("sessionEmployee") Employee sessionEmployee, RedirectAttributes redirectAttributes){
        LocalDate ld=LocalDate.now();
        String pDate=ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        LocalTime lt=LocalTime.now();
        String pTime=lt.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));

        String pDate_Time=pDate+", "+pTime;
        order.setDateOfPurchase(pDate_Time);
        try {
            EmployeeOrders employeeOrders=new EmployeeOrders();
            employeeOrders.setOrderId(order.getOrderId());
            employeeOrders.setEmployeeEmail(sessionEmployee.getEmail());
            employeeOrdersRepository.save(employeeOrders);

            ordersService.storeUserOrder(order);
            redirectAttributes.addFlashAttribute("successMsg","Course provided successfully");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMsg","Course not provided due to some error");
        }
        return "redirect:/sellCourse";
    }

    @GetMapping("/inquiryManagement")
    public String handleInquiryManagement(Model model){
        if(sessionEmployee==null) return "employee-login";
        else{
            model.addAttribute("inquiry",new Inquiry());
            return "inquiry-management";
        }
    }

    @GetMapping("/manageEmployee")
    public String handleManageEmployee(Model model,@RequestParam(name = "page",defaultValue = "0") int page,
                                                   @RequestParam(name = "size",defaultValue = "3") int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Employee> list=employeeService.getAllEmployeeByPageination(pageable);
        model.addAttribute("allEmployee",list);
        return "manage-employee";
    }

    @GetMapping("/addEmployee")
    public String handleAddEmployee(Model model){
        model.addAttribute("employee",new Employee());
        return "add-employee";
    }

    @PostMapping("/addEmployeeForm")
    public String handleAddEmployee(@ModelAttribute("employee") Employee employee,Model model){
        try{
            employeeService.addEmployee(employee);
            model.addAttribute("successMsg","Employee added successfully");
        }catch (Exception e){
            model.addAttribute("errorMsg","Employee not added due to some error");
            e.printStackTrace();
        }
        return "add-employee";
    }

    @GetMapping("/editEmployee")
    public String handleEditEmployee(@RequestParam("employeeEmail") String emplEmail,Model model){
        Employee employee=employeeService.getEmployeeByEmail(emplEmail);
        model.addAttribute("employee",employee);
        return "edit-employee";
    }

    @PostMapping("/editEmployeeForm")
    public String editEmployeeForm(@ModelAttribute("editEmployee") Employee updatedEmployeeObj, RedirectAttributes redirectAttributes){
        Employee oldEmployeeObj=employeeService.getEmployeeByEmail(updatedEmployeeObj.getEmail());
        updatedEmployeeObj.setId(oldEmployeeObj.getId());
        try {
            employeeService.updatedEmployeeByEmail(updatedEmployeeObj);
            redirectAttributes.addFlashAttribute("successMsg","Employee updated successfully");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMsg","Employee not updated due to some error");
            e.printStackTrace();
        }
        return "redirect:/manageEmployee";
    }

    @GetMapping("/deleteEmployeeDetails")
    public String handleDeleteEmployee(@RequestParam("employeeEmail") String email,RedirectAttributes redirectAttributes){
        try{
            employeeService.deleteEmployeeByEmail(email);
            redirectAttributes.addFlashAttribute("successMsg","Course deleted successfully");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMsg","Course not deleted due to some error");
            e.printStackTrace();
        }
        return "redirect:/manageEmployee";
    }
}
