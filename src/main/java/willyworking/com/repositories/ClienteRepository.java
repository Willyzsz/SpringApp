package willyworking.com.repositories;

import willyworking.com.models.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    @Query("SELECT cl FROM Cliente cl WHERE cl.nombre LIKE %:nombre%")
    List<Cliente> findByNombreLike(@Param("nombre") String nombre);
}
