package in.sk.main.services;

import in.sk.main.entities.Orders;
import in.sk.main.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public void storeUserOrder(Orders order){
        ordersRepository.save(order);
    }

    public List<Object[]> getPuchasedCourse(String email){
        return ordersRepository.selectPurchaseCourse(email);
    }

    public List<Object[]> findPurchasedCourseByEmai(String email){
        return ordersRepository.findByUser_email(email);
    }
}
