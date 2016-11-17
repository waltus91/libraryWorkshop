package globantWorkshop.models.repositories;

import globantWorkshop.models.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Walter on 14/11/2016.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    Book findByName(String name);

    Book findByIsbn(Integer isbn);

    Iterable<Book> findByAuthor(String author);
}
