package in.sk.main.controller;

import in.sk.main.services.OrdersChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrdersChartController {

    @Autowired
    private OrdersChartService ordersChartService;

    @GetMapping("/dashBoard")
    public String openAdminProfilePage(Model model) {

        //---------Bar chart---------------------------
        List<Object[]> listOfCourseAmountSale=ordersChartService.courseAmountTotalSale();

        List<String> dateRevenue=new ArrayList<>();
        List<Double> totalRevenue=new ArrayList<>();

        for(Object[] obj:listOfCourseAmountSale){
            dateRevenue.add((String)(obj[0]));
            totalRevenue.add((Double)(obj[1]));
        }
        model.addAttribute("dateRevenue",dateRevenue);
        model.addAttribute("totalRevenue",totalRevenue);

        //---------Pie chart---------------------------
        List<Object[]> listOfCourseTotalSale=ordersChartService.findCourseTotalSale();

        List<String> courseName=new ArrayList<>();
        List<Long> courseCount=new ArrayList<>();

        for(Object[] obj:listOfCourseTotalSale){
            courseName.add((String)(obj[0]));
            courseCount.add((Long)(obj[1]));
        }
        model.addAttribute("courseName",courseName);
        model.addAttribute("courseCount",courseCount);

        //---------Line chart ------------------
        List<Object[]> listOfCourseSoldPerDay=ordersChartService.findCourseSoldPerDay();

        List<String> dates=new ArrayList<>();
        List<Long> counts=new ArrayList<>();

        for(Object[] obj:listOfCourseSoldPerDay){
            dates.add((String)(obj[0]));
            counts.add((Long)(obj[1]));
        }
        model.addAttribute("dates",dates);
        model.addAttribute("counts",counts);

        return "admin-profile";
    }
}
