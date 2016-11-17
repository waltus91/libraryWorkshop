package globantWorkshop.controllers;

import globantWorkshop.models.entities.Order;
import globantWorkshop.services.interfaces.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Walter
 */

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private OrderServiceInterface orderService;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Order> getAll() {
        return orderService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order get(@PathVariable("id") Order order) {
        return order;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Order create(Order order) {
        return orderService.save(order);
    }

    @RequestMapping(value = "/{id}/return", method = RequestMethod.GET)
    public String returned(@PathVariable("id") Integer orderId) {
        return orderService.returnBook(orderId);
    }
}
