package in.sk.main.services;

import in.sk.main.entities.Feedback;
import in.sk.main.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class FeedbackServices {

    @Autowired
    FeedbackRepository feedbackRepository;

    public void addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public Page<Feedback> getAllFeedback(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    public boolean getFeedbackById(Long id,String status) {
        Feedback feedback = feedbackRepository.findById(id).orElse(null);
        if(feedback != null) {
            feedback.setReadStatus(status);
            feedbackRepository.save(feedback);
            return true;
        }
        return false;
    }
}
