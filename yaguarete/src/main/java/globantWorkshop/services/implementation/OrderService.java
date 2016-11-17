package globantWorkshop.services.implementation;

import globantWorkshop.models.entities.Order;
import globantWorkshop.models.repositories.OrderRepository;
import globantWorkshop.services.interfaces.BookServiceInterface;
import globantWorkshop.services.interfaces.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

/**
 * Created by Walter
 */
@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookServiceInterface bookService;

    @Override
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Integer orderId) {
        return orderRepository.findOne(orderId);
    }

    @Override
    public Order save(Order order) throws TransactionException {
        Order newOrder = orderRepository.save(order);
        bookService.disable(newOrder.getBook());
        return newOrder;
    }

    @Override
    public String returnBook(Integer orderId) {
        Order order = findById(orderId);
        order.setReturned(true);
        orderRepository.save(order);
        bookService.enable(order.getBook());
        return "Book returned";
    }
}
