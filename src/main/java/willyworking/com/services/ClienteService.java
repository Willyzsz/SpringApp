package willyworking.com.services;

import willyworking.com.models.Cliente;
import willyworking.com.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Cliente> findByNombreLike(String nombre) {
        return (List<Cliente>) repository.findByNombreLike(nombre);
    }

    public Cliente findByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}