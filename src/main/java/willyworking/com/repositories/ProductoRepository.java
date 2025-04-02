package willyworking.com.repositories;

import willyworking.com.models.Producto;
import willyworking.com.models.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {
    List<Producto> findAll();

    @Query("SELECT p FROM Producto p ORDER BY p.precio DESC")
    List<Producto> OrderByPrecioDesc();

    @Query("SELECT p FROM Producto p ORDER BY p.precio ASC")
    List<Producto> OrderByPrecioAsc();

    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    List<Producto> findAllWithStock();

    @Query("SELECT p FROM Producto p WHERE p.nombreProducto LIKE %:nombreProducto%")
    List<Producto> findByNombreLike(@Param("nombreProducto") String nombreProducto);

    @Query("SELECT cl.nombre FROM Cliente cl")
    List<Cliente> findAllWithCliente();

}
