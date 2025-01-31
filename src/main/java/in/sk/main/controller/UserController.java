package in.sk.main.controller;

import in.sk.main.dto.PurchasedCourse;
import in.sk.main.entities.Course;
import in.sk.main.entities.User;
import in.sk.main.repositories.UserRepository;
import in.sk.main.services.CourseService;
import in.sk.main.services.OrdersService;
import in.sk.main.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("sessionUser")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private OrdersService ordersService;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @GetMapping({"/","/index"})
    public String openHomePage(Model model, @SessionAttribute(value = "sessionUser" ,required = false) User sessionUser){
        List<Course> courseList=courseService.getAllCourse();
        model.addAttribute("allCourseList",courseList);

        if(sessionUser!=null){
            List<Object[]> list=ordersService.getPuchasedCourse(sessionUser.getEmail());
            List<String> purchasedCoursesNameList=new ArrayList<>();
            for(Object[] course:list) {
                purchasedCoursesNameList.add((String) course[2]);
            }
            model.addAttribute("razorpayKey",razorpayKey);
            model.addAttribute("purchasedCoursesNameList",purchasedCoursesNameList);
        }
        return "index";
    }

    //---------REGISTER PART-------------------
    @GetMapping("/register")
    public String openRegisterPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/regForm")
    public String handleRegForm(@Valid @ModelAttribute("user") User user,BindingResult result, Model model,@RequestParam("isCustomer") String isCustomer){
        if(result.hasErrors()){
            return "register";
        }else{
            try{
                user.setBanStatus(false);
                userService.registerUserService(user);
                model.addAttribute("successMsg","Registered successfully");
//            return "redirect:/register";
                if(isCustomer.equals("customer")){
                    return "redirect:/manageCustomer";
                }
                else return "register";
            }catch (Exception e){
                e.printStackTrace();
                model.addAttribute("failedMsg","Registration Failed");
                if(isCustomer.equals("customer")){
                    return "redirect:/manageCustomer";
                }
                else return "error";
            }
        }
    }
    //---------REGISTER PART-------------------

    //---------LOGIN PART-------------------
    @GetMapping("/login")
    public String openLoginPage(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/loginForm")
    public String handleLoginForm(@ModelAttribute("user") User user,Model model){
        boolean status= userService.loginUserService(user.getEmail(),user.getPassword());
        if(status){
            User authenticateUser=userRepository.findByEmail(user.getEmail());
            if(authenticateUser.isBanStatus()){
                model.addAttribute("errorMsg","Sorry, your account is banned, please contact to admin, thank you...!!");
                return "login";
            }
            else {
                model.addAttribute("sessionUser",authenticateUser);
                return "user-profile";
            }

        }else{
            model.addAttribute("errorMsg","Incorrect Email id or Password");
            return "login";
        }
    }


    //---------LOGIN PART-------------------

    //---------LOGOUT PART-------------------

    @GetMapping("/logout")
    public String handleLogout(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "login";
    }

    //---------LOGOUT PART-------------------

    @GetMapping("/myCourses")
    public String myCoursesPage(@ModelAttribute("sessionUser") User user,Model model){
        List<Object[]> pCDList=ordersService.getPuchasedCourse(user.getEmail());

        List<PurchasedCourse> purchasedCoursesList=new ArrayList<>();
        for(Object[] course:pCDList){
            PurchasedCourse purchasedCourse=new PurchasedCourse();

            purchasedCourse.setDescription((String) course[0]);
            purchasedCourse.setImageUrl((String) course[1]);
            purchasedCourse.setCourseName((String) course[2]);
            purchasedCourse.setUpdatedOn((String) course[3]);
            purchasedCourse.setPurchasedOn((String) course[4]);

            purchasedCoursesList.add(purchasedCourse);
        }
        model.addAttribute("purchasedCoursesList",purchasedCoursesList);
        return "my-courses";
    }

    @GetMapping("/myProfile")
    public String userProfile(){
        return "user-profile";
    }



}
