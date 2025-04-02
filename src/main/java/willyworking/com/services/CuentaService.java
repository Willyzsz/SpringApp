package willyworking.com.services;

import willyworking.com.models.Cuenta;
import willyworking.com.models.Cliente;
import willyworking.com.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository repository;


    public Cuenta findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Cliente> findAllClientes() {
        return (List<Cliente>) repository.findAllClientes();
    }

    public List<Cuenta> findAllWithCliente(String nombre) {
        return (List<Cuenta>) repository.findAllWithCliente(nombre);
    }

    public Cuenta findByIdWithCliente(Long id) {
        return repository.findByIdWithCliente(id);
    }

    public List<Cuenta> OrderByMontoDesc() {
        return (List<Cuenta>) repository.OrderByMontoDesc();
    }

    public List<Cuenta> OrderByMontoAsc() {
        return (List<Cuenta>) repository.OrderByMontoAsc();
    }

    public Cuenta save(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}