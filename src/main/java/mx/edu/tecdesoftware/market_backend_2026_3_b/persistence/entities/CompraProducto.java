package mx.edu.tecdesoftware.market_backend_2026_3_b.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "compras_productos")
public class CompraProducto {

    @EmbeddedId
    private CompraProductoPK id;


    private Integer cantidad;
    private Double total;
    private Boolean estado;

}
