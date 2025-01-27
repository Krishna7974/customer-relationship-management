package in.sk.main.services;

import in.sk.main.repositories.OrdersChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersChartService {

    @Autowired
    private OrdersChartRepository ordersChartRepository;

    public List<Object[]> findCourseSoldPerDay(){
        return ordersChartRepository.courseSoldPerDay();
    }

    public List<Object[]> findCourseTotalSale(){
        return ordersChartRepository.courseTotalSale();
    }

    public List<Object[]> courseAmountTotalSale(){
        return ordersChartRepository.courseAmountTotalSale();
    }
}
