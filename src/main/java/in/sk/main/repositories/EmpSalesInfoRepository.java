package in.sk.main.repositories;

import in.sk.main.entities.EmployeeOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpSalesInfoRepository extends JpaRepository<EmployeeOrders,Long> {

    String SQL_QUIRY1="select sum(course_amount) as total_sum_of_course_amount from orders where order_id not like 'order_%'";
    String SQL_QUIRY2="select e.name as employee_name,e.email as employee_email,e.phoneno as employee_phoneno, sum(o.course_amount) as total_sale from employee e join employee_orders eo on e.email=eo.employee_email join orders o on eo.order_id=o.order_id group by e.name,e.email,e.phoneno";

    @Query(value = SQL_QUIRY1,nativeQuery = true)
    public String totalSalesByAllEmployees();

    @Query(value = SQL_QUIRY2,nativeQuery = true)
    public List<Object[]> totalSaleByEachEmployee();
}
