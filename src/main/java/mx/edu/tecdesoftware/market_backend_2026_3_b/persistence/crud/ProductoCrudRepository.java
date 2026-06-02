package mx.edu.tecdesoftware.market_backend_2026_3_b.persistence.crud;

import mx.edu.tecdesoftware.market_backend_2026_3_b.persistence.entities.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    //Quiero tener la lista de productos filtrdos por id de categoria y ordenados ascendentemente por nombre
    /*
        SELECT *
        FROM Categoria
        WHERE id_categoria = ?
        ORDER BY Nombre ASC
     */

    List<Producto> findByCantidadOrderByNombreAsc(int idCategoria);

    //Obtener los productos
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidad, boolean estado);

}
