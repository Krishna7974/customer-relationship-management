package in.sk.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sk.main.repositories.EmpSalesInfoRepository;

import java.util.List;

@Service
public class EmpSalesInfoService {

    @Autowired
    private EmpSalesInfoRepository empSalesInfoRepository;

    public String findTotalSalesByAllEmployees(){
        return empSalesInfoRepository.totalSalesByAllEmployees();
    }

    public List<Object[]> findTotalSaleByEachEmployee(){
        return empSalesInfoRepository.totalSaleByEachEmployee();
    }
}
