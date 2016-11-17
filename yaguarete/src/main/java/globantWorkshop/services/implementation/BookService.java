package globantWorkshop.services.implementation;

import globantWorkshop.models.dao.BookDao;
import globantWorkshop.models.entities.Book;
import globantWorkshop.models.repositories.BookRepository;
import globantWorkshop.services.interfaces.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandromaro on 29/10/16.
 */
@Service
public class BookService implements BookServiceInterface {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    BookDao bookDao;

    @Autowired
    BookRepository bookRepository;

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    @Override
    public ArrayList<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        try {
            books = bookDao.getAllBooks();
        } catch (Exception ex) {
            throw ex;
        }
        return (ArrayList<Book>) books;
    }

    @Override
    public Book create(Book book) throws PersistenceException {
        bookDao.create(book);
        return book;
    }

    @Override
    public String delete(Integer idBook) {
        bookDao.delete(findBookById(idBook));
        return "Book successfully deleted!";
    }

    @Override
    public String updateBook(Book newBook){
        try {
            bookDao.update(newBook);
            return "Book successfully updated!";
        } catch (Exception ex) {
            return "Error updating the book " + ex.toString();
        }
    }

    @Override
    public Book findBookById(int bookId) throws TransactionSystemException {
        Book book;
        try {
            book = bookDao.getById(bookId);
        } catch (TransactionSystemException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
        return book;
    }


    // Repository methods
    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Integer id) {
        return bookRepository.findOne(id);
    }

    @Override
    public String save(Book book) throws PersistenceException {
        try {
            book.setEnable(true);
            bookRepository.save(book);
            return "Book successfully registered!";
        } catch (Exception ex) {
            return "Error registering the book " + ex.toString();
        }
    }

    @Override
    public String update(Book book) {
        try {
            bookRepository.save(book);
            return "Book successfully updated!";
        } catch (Exception ex) {
            return "Error updating the book " + ex.toString();
        }
    }

    @Override
    public String delete(Book book) {
        try {
            bookRepository.delete(book);
            return "Book successfully deleted!";
        } catch (Exception ex) {
            return "Error deleting the book " + ex.toString();
        }
    }

    @Override
    public String disable(Book book) {
        try {
            book.setEnable(false);
            bookRepository.save(book);
            return "Book successfully disabled!";
        } catch (Exception ex) {
            return "Can't disable the book";
        }
    }

    @Override
    public String enable(Book book) {
        try {
            book.setEnable(true);
            bookRepository.save(book);
            return "Book successfully enabled!";
        } catch (Exception ex) {
            return "Can't enable the book";
        }
    }

    @Override
    public Book findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public Book findByIsbn(Integer isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Iterable<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
}
