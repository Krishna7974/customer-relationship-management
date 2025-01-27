package in.sk.main.controller;

import in.sk.main.entities.Employee;
import in.sk.main.entities.FollowUp;
import in.sk.main.entities.Inquiry;
import in.sk.main.services.FollowUpService;
import in.sk.main.services.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@SessionAttributes("sessionEmployee")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private FollowUpService followUpService;

    @GetMapping("/newInquiry")
    public String opneNewInquiry(Model model){
        model.addAttribute("inquiry",new Inquiry());
        return "new-inquiry";
    }

    @PostMapping("/newInquiryForm")
    public String handleNewInquiryForm(@ModelAttribute("inquiry") Inquiry inquiry,
                                       @SessionAttribute("sessionEmployee")Employee employee,
                                       @RequestParam(name = "followUpDate",required = false) String followUpDate,
                                       @RequestParam(name = "sourcePage",value = "",required = false) String sourcePage,
    Model model){
        System.out.println("hello");
        inquiry.setEmployeeEmail(employee.getEmail());

        LocalDate ld=LocalDate.now();
        String date=ld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        LocalTime lt=LocalTime.now();
        String time=lt.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        inquiry.setDateOfInquiry(date);
        inquiry.setTimeOfInquiry(time);
        try {
            inquiryService.addNewInquiry(inquiry);
            String status=inquiry.getStatus();
            if(status.equals("Interested - (follow up)") && followUpDate!=null){
                FollowUp followUp=new FollowUp();
                followUp.setPhoneno(inquiry.getPhoneno());
                followUp.setFollowUpDate(followUpDate);
                followUp.setEmployeeEmail(employee.getEmail());

                followUpService.addFollowUps(followUp);
            }else{
                followUpService.deleteFollowUps(inquiry.getPhoneno());
            }
            model.addAttribute("successMsg","New inquiry added successfully");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMsg","Some error occured, please try again later");
        }
        if("followUpsPage".equals(sourcePage)){
            model.addAttribute("successMsg","Inquiry handled successfully");
            return "follow-ups";
        }
        else return "inquiry-management";
    }
}
