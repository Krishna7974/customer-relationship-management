package in.sk.main.repositories;

import in.sk.main.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {

    String SELECT_QUERY1="select c.description,c.image_url,c.name,c.updated_on,o.date_of_purchase from orders o join course c on o.course_name=c.name where o.user_email=:email";

    @Query(value = SELECT_QUERY1,nativeQuery = true)
    List<Object[]> selectPurchaseCourse(@Param("email") String email);

    String SELECT_QUERY2="select c.image_url,o.course_name,o.course_amount,o.date_of_purchase,o.order_id,o.payment_id,u.name from (orders o join course c on o.course_name=c.name) join user u on o.user_email=u.email where o.user_email=:email";

    @Query(value = SELECT_QUERY2,nativeQuery = true)
    List<Object[]> findByUser_email(@Param("email")String email);
}
