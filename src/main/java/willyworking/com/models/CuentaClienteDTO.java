package willyworking.com.models;

public class CuentaClienteDTO {
    private Long idCuenta;
    private Long idCliente;
    private String nombreCliente;

    public CuentaClienteDTO(Long idCuenta, Long idCliente, String nombreCliente) {
        this.idCuenta = idCuenta;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
    }

    public Long getIdCuenta() { return idCuenta;}
    public void setIdCuenta(Long idCuenta) { this.idCuenta = idCuenta;}

    public Long getIdCliente() { return idCliente;}
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente;}

    public String getNombreCliente() { return nombreCliente;}
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente;}
}



