package willyworking.com.models;

import jakarta.persistence.*;

@Entity
@Table(name="clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idCliente")
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    @Column(name="nombreCliente")
    private String nombre;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre;  }


    @Column(name="numero")
    private int numero;

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
}
