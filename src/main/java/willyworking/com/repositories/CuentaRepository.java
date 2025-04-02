package willyworking.com.repositories;

import willyworking.com.models.Cuenta;
import willyworking.com.models.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Long> {

    @Query("SELECT c FROM Cuenta c ORDER BY c.monto DESC")
    List<Cuenta> OrderByMontoDesc();

    @Query("SELECT c FROM Cuenta c ORDER BY c.monto ASC")
    List<Cuenta> OrderByMontoAsc();

    @Query("SELECT c FROM Cuenta c JOIN FETCH c.cliente WHERE c.cliente.nombre LIKE %:nombre%")
    List<Cuenta> findAllWithCliente(@Param("nombre") String nombre);

    @Query("SELECT cl FROM Cliente cl")
    List<Cliente> findAllClientes();
}
