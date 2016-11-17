package globantWorkshop.models.repositories;

import globantWorkshop.models.entities.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Walter
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
