package in.sk.main.repositories;

import in.sk.main.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersChartRepository extends JpaRepository<Orders,Long> {

    String SQL_QUIRY1="select substring_index(date_of_purchase,',',1) as purchased_date, sum(course_amount) as total_sales_amount from orders group by purchased_date order by purchased_date";

    @Query(value = SQL_QUIRY1,nativeQuery = true)
    public List<Object[]> courseAmountTotalSale();


    String SQL_QUIRY2="select course_name,count(*) as total_sold from orders group by course_name";

    @Query(value = SQL_QUIRY2,nativeQuery = true)
    public List<Object[]> courseTotalSale();


    String SQL_QUIRY3="select substring_index(date_of_purchase,',',1) as purchased_date,count(*) as number_of_courses_sold from orders group by purchased_date order by purchased_date";

    @Query(value = SQL_QUIRY3,nativeQuery = true)
    public List<Object[]> courseSoldPerDay();

}
