package globantWorkshop.models.repositories;

import globantWorkshop.models.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Walter on 14/11/2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
