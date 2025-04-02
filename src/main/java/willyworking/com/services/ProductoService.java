package willyworking.com.services;

import willyworking.com.models.Producto;
import willyworking.com.models.Cliente;
import willyworking.com.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository repository;

    public List<Producto> findAll() {
        return repository.findAll();
    }

    public Producto findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Producto> findByNombreLike(String nombreProducto) {
        return (List<Producto>) repository.findByNombreLike(nombreProducto);
    }

    public List<Producto> findAllWithStock() {
        return (List<Producto>) repository.findAllWithStock();
    }
    
    public List<Producto> OrderByPrecioDesc() {
        return (List<Producto>) repository.OrderByPrecioDesc();
    }

    public List<Producto> OrderByPrecioAsc() {
        return (List<Producto>) repository.OrderByPrecioAsc();
    }

    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Cliente> findAllWithCliente() {
        return (List<Cliente>) repository.findAllWithCliente();
    }

}