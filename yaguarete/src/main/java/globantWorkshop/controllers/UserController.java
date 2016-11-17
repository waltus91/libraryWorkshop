package globantWorkshop.controllers;

import globantWorkshop.models.entities.User;
import globantWorkshop.services.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


/**
 * Class UserController
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private UserServiceInterface libraryService;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    /**
     * Get all the users
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAllUsers() {
        return libraryService.getAllUsers();
    }

    /**
     * Create a new user with an auto-generated id and email and name as passed
     * values.
     * ATTENTION: The better way to access a post request it's using a wrapper as @RequestBody parameter,
     * take a look at http://stackoverflow.com/questions/5726583/spring-rest-multiple-requestbody-parameters-possible
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> create(@RequestBody User userParam) throws PersistenceException {
        libraryService.create(userParam);
        return new ResponseEntity<User>(userParam,HttpStatus.CREATED);
    }


    /**
     * Delete the user with the passed id.
     * ATTENTION: The better way to access a post request it's using a wrapper as @RequestBody parameter,
     * but, here we only want to pass the id value, so we handle the id using the JSONObject class.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete (@PathVariable Integer id) {
        return libraryService.delete(id);
    }

    /**
     * Update the user's data for the user ipassed as parameter.
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String updateName(@RequestBody User userParam) {
        return libraryService.updateUserValues(userParam);
    }

    /**
     * Method created to handle the controller's exceptions, so the malformed request are responded in the controller layer
     * @param response HttpStatus.BAD_REQUEST
     * @throws IOException
     */
    @ExceptionHandler({MissingServletRequestParameterException.class,TransactionSystemException.class,IllegalArgumentException.class, PersistenceException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("users", libraryService.getAllUsers());
        return "users";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        return "user-form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid User user, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            return "user-form";
        }
        if (user.getIdUser() == null) {
            attributes.addFlashAttribute("message", libraryService.create(user));
        } else {
            attributes.addFlashAttribute("message", libraryService.updateUserValues(user));
        }

        return "redirect:/users/all";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", libraryService.delete(id));
        return "redirect:/users/all";
    }
}
