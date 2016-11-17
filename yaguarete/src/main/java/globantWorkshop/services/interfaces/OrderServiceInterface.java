package globantWorkshop.services.interfaces;

import globantWorkshop.models.entities.Order;

/**
 * Created by Walter
 */

public interface OrderServiceInterface {

    Iterable<Order> findAll();

    Order findById(Integer orderId);

    Order save(Order order);

    String returnBook(Integer orderId);
}
