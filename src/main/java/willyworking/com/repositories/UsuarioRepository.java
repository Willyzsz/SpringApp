package willyworking.com.repositories;

import willyworking.com.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario findByUsuario(String usuario);
}
