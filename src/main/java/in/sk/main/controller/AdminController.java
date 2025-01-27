package in.sk.main.controller;

import in.sk.main.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class AdminController {

    private String sessionEmail="";

    @GetMapping("/adminLogin")
    public String adminLogin(Model model){
        model.addAttribute("user",new User());
        return "admin-login";
    }

    @PostMapping("/adminLoginForm")
    public String handleAdminLogin(@ModelAttribute("user") User user, Model model){
        if(user.getEmail().equals("admin@gmail.com") && user.getPassword().equals("admin123")){
            sessionEmail= user.getEmail();
            return "redirect:/dashBoard";
        }else{
            model.addAttribute("errorMsg","Incorrect Email id or Password");
            return "admin-login";
        }
    }


    @GetMapping("/adminLogout")
    public String handleAdminLogout(SessionStatus sessionStatus){
        sessionEmail = "";
        sessionStatus.setComplete();
        return "admin-login";
    }

}
