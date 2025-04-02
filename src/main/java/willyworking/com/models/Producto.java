package willyworking.com.models;

import jakarta.persistence.*;


@Entity
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idProducto")
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    @Column(name="nombreProducto")
    private String nombreProducto;

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    @Column(name="precio")
    private double precio;

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio;  }


    @Column(name="stock")
    private int stock;

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock;  }

}
