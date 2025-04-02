package willyworking.com.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idCuenta")
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name="idCliente")
    private Cliente cliente;

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Column(name="monto")
    private double monto;

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto;  }

    @Column(name="fechaHora")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaHora;

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
