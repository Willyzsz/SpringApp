package willyworking.com.models;

import jakarta.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idUsuario")
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    @Column(name="nombreUsuario")
    private String usuario;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    @Column(name="contrasena")
    private String contrasena;

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena;  }


    @Column(name="rolUsuario")
    private String rolUsuario;

    public String getRolUsuario() { return rolUsuario; }
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario;  }


}
