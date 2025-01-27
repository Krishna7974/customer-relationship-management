package in.sk.main.repositories;

import in.sk.main.entities.EmployeeOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeOrdersRepository extends JpaRepository<EmployeeOrders,Long> {
}
