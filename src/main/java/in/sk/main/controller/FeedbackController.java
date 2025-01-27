package in.sk.main.controller;

import in.sk.main.entities.Feedback;
import in.sk.main.services.FeedbackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@SessionAttributes("sessionUser")
public class FeedbackController {

    @Autowired
    FeedbackServices feedbackServices;

    @GetMapping("/feedbackPage")
    public String userFeedback(Model model) {
        model.addAttribute("feedbackData",new Feedback());
        return "feedback";
    }

    @PostMapping("/feedbackForm")
    public String feedbackForm(@ModelAttribute("feedbackData") Feedback feedback, Model model) {
        feedback.setDateOfFeedback(LocalDate.now().toString());
        feedback.setTimeOfFeedback(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        feedback.setReadStatus("unread");
        try{
            feedbackServices.addFeedback(feedback);
            model.addAttribute("successMsg", "Feedback submited successfully");
        }catch(Exception e){
            model.addAttribute("successMsg", "Feedback not submitted");
        }
        return "feedback";
    }

    @GetMapping("/feedbacks")
    public String allFeedbacks(@RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "size",defaultValue = "5") int size, Model model) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Feedback> feedbacksList = feedbackServices.getAllFeedback(pageable);
        model.addAttribute("feedbacksList",feedbacksList);
        return "view-feedbacks";
    }

    @PostMapping("/updateFeedbackStatus")
    public String updateFeedbackStatus(@RequestParam("id") Long id, @RequestParam("status") String status, RedirectAttributes redirectAttributes) {

        boolean success = feedbackServices.getFeedbackById(id, status);
        if (success) {
            redirectAttributes.addFlashAttribute("successMsg", "Feedback updated successfully");
        }else{
            redirectAttributes.addFlashAttribute("errorMsg", "Feedback not updated");
        }
        return "redirect:/feedbacks";
    }
}
