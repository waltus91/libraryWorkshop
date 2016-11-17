package globantWorkshop.controllers;

import globantWorkshop.models.entities.Book;
import globantWorkshop.services.implementation.BookService;
import globantWorkshop.services.interfaces.BookServiceInterface;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandromaro on 29/10/16.
 */
@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    BookServiceInterface bookService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Book getById(@PathVariable("id") Integer bookId) {
        return bookService.findBookById(bookId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    /**
     * Create a new book with an auto-generated id
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book newBook = bookService.create(book);
        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }

    /**
     * Delete the user with the passed id.
     * ATTENTION: The better way to access a post request it's using a wrapper as @RequestBody parameter,
     * but, here we only want to pass the id value, so we handle the id using the JSONObject class.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete (@PathVariable("id") Integer bookId) {
        return bookService.delete(bookId);
    }

    /**
     * Update the book's data for the book passed as parameter.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateName(@PathVariable("id") Book bookId, @RequestBody Book bookParam){
        return bookService.updateBook(bookParam);
    }

    /**
     * Method created to handle the controller's exceptions, so the malformed request are responded in the controller layer
     * @param response HttpStatus.BAD_REQUEST
     * @throws IOException
     */
    @ExceptionHandler({MissingServletRequestParameterException.class,TransactionSystemException.class,IllegalArgumentException.class, NullPointerException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String updateBook(@PathVariable("id") Book book, Model model) {
        model.addAttribute("book", book);
        return "book-form";
    }

    // Repository methods
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public Book get(@PathVariable("id") Book book) {
        return book;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid Book book, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            return "book-form";
        }
        if (bookService.findByIsbn(book.getIsbn()) != null) {
            attributes.addFlashAttribute("message", "ISBN already exist!");
            return "redirect:/books/create";
        }
        if (book.getIdbooks() == null) {
            attributes.addFlashAttribute("message", bookService.save(book));
        } else {
            attributes.addFlashAttribute("message", bookService.update(book));
        }

        return "redirect:/books/all";
    }

    @RequestMapping(value = "/{id}/delete")
    public String remove(@PathVariable("id") Book book, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", bookService.delete(book));
        return "redirect:/books/all";
    }

    @RequestMapping(value = "/{id}/disable", method = RequestMethod.GET)
    public String disable(@PathVariable("id") Book book, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", bookService.disable(book));
        return "redirect:/books/all";
    }

    @RequestMapping(value = "/{id}/enable", method = RequestMethod.GET)
    public String enable(@PathVariable("id") Book book, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", bookService.enable(book));
        return "redirect:/books/all";
    }

    @RequestMapping(value = "/view/{name}", method = RequestMethod.GET)
    public Book getByName(@PathVariable("name") String name) {
        return bookService.findByName(name);
    }

    @RequestMapping(value = "/view/{isbn}", method = RequestMethod.GET)
    public Book getByIsbn(@PathVariable("isbn") Integer isbn) {
        return bookService.findByIsbn(isbn);
    }

    @RequestMapping(value = "/view/{author}", method = RequestMethod.GET)
    public Iterable<Book> getByAuthor(@PathVariable("author") String author) {
        return bookService.findByAuthor(author);
    }
}
