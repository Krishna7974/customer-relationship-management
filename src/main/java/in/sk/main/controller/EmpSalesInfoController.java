package in.sk.main.controller;

import in.sk.main.services.EmpSalesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmpSalesInfoController {

    @Autowired
    private EmpSalesInfoService empSalesInfoService;

    @GetMapping("/salesPage")
    public String openSalesPage(Model model){
        String totalSales=empSalesInfoService.findTotalSalesByAllEmployees();
        model.addAttribute("totalSales",totalSales);

        List<Object[]> salesList=empSalesInfoService.findTotalSaleByEachEmployee();
        model.addAttribute("salesList",salesList);
        return "sales";
    }
}
