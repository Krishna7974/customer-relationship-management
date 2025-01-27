package in.sk.main.controller;

import in.sk.main.dto.PurchasedCourseByCustomer;
import in.sk.main.entities.User;
import in.sk.main.services.OrdersService;
import in.sk.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/manageCustomer")
    public String getAllUser(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> paginatedUserList = userService.getAllUserService(pageable);
        model.addAttribute("customerList", paginatedUserList);
        return "manage-customer";
    }

    @GetMapping("/addCustomer")
    public String addCustomer(Model model) {
        model.addAttribute("user", new User());
        return "add-customer";
    }

    @GetMapping("/editCustomer")
    public String editCustomer(@RequestParam("customerEmail") String customerEmail, Model model) {
        User customer = userService.getUserByEmailService(customerEmail);
        model.addAttribute("customer", customer);
        return "edit-customer";
    }

    @PostMapping("/editCustomerForm")
    public String editCustomerForm(@ModelAttribute("editCustomer") User customer, RedirectAttributes redirectAttributes) {
        User oldCustomer = userService.getUserByEmailService(customer.getEmail());
        customer.setId(oldCustomer.getId());
        try {
            userService.registerUserService(customer);
            redirectAttributes.addFlashAttribute("successMsg", "Customer updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMsg", "Customer not updated due to some error");
        }
        return "redirect:/manageCustomer";
    }

    @GetMapping("/moreCustomer")
    public String moreCustomerPage(@RequestParam("customerEmail") String customerEmail, Model model) {
        List<Object[]> purchasedCourse = ordersService.findPurchasedCourseByEmai(customerEmail);
        List<PurchasedCourseByCustomer> purchasedCourseByCustomerList = new ArrayList<>();
        String customerName = "";
        if (!purchasedCourse.isEmpty()) {
            for (Object[] course : purchasedCourse) {
                PurchasedCourseByCustomer purchasedCourseByCustomer = new PurchasedCourseByCustomer();

                purchasedCourseByCustomer.setImage_url((String) course[0]);
                purchasedCourseByCustomer.setCourseName((String) course[1]);
                customerName = (String) course[6];
                purchasedCourseByCustomer.setOrderId((String) course[4]);
                purchasedCourseByCustomer.setPaymentId((String) course[5]);
                purchasedCourseByCustomer.setDateOfPurchase((String) course[3]);
                purchasedCourseByCustomer.setCourseAmount((String) course[2]);

                purchasedCourseByCustomerList.add(purchasedCourseByCustomer);
            }
            model.addAttribute("purchasedCourseByCustomerList", purchasedCourseByCustomerList);
            model.addAttribute("customerName", customerName);
        } else model.addAttribute("errorMsg", "Sorry,This customer didn't purchased any course");
        return "coursesBy-customer";
    }

    @GetMapping("/banUserDetails")
    public String banUserDetails(@RequestParam("userEmail") String userEmail, @RequestParam("banStatus") boolean banStatus, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserByEmailService(userEmail);
            user.setBanStatus(banStatus);
            userService.updateUserBanStatus(user);

            if (banStatus) {
                redirectAttributes.addFlashAttribute("successMsg", "User Banned successfully");
            } else {
                redirectAttributes.addFlashAttribute("successMsg", "User Unbanned successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMsg", "User Not Banned due to some error");
        }
        return "redirect:/manageCustomer";
    }
}
