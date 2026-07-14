package mx.edu.tecdesoftware.market_backend_2026_3_b.persistence;

import mx.edu.tecdesoftware.market_backend_2026_3_b.domain.Product;
import mx.edu.tecdesoftware.market_backend_2026_3_b.domain.repository.ProductRepository;
import mx.edu.tecdesoftware.market_backend_2026_3_b.persistence.crud.ProductoCrudRepository;
import mx.edu.tecdesoftware.market_backend_2026_3_b.persistence.entities.Producto;
import mx.edu.tecdesoftware.market_backend_2026_3_b.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
// Le da acceso a la base de datos
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    // Obtener todos los productos
    @Override
    public List<Product> getAll() {
        List<Producto> productos = new ArrayList<>();

        productoCrudRepository.findAll().forEach(productos::add);

        return productMapper.toProducts(productos);
    }

    // Obtener productos por categoría
    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos =
                productoCrudRepository
                        .findByIdCategoriaOrderByNombreAsc(categoryId);

        if (productos.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(productMapper.toProducts(productos));
    }

    // Obtener productos escasos y activos
    @Override
    public Optional<List<Product>> getScarceProducts(int quantity) {
        return productoCrudRepository
                .findByCantidadStockLessThanAndEstado(quantity, true)
                .filter(productos -> !productos.isEmpty())
                .map(productMapper::toProducts);
    }

    // Obtener un producto por su ID
    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository
                .findById(productId)
                .map(productMapper::toProduct);
    }

    // Guardar un producto nuevo
    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);

        // El ID debe ser null para que PostgreSQL lo genere.
        producto.setIdProducto(null);

        Producto productoGuardado =
                productoCrudRepository.save(producto);

        return productMapper.toProduct(productoGuardado);
    }

    // Eliminar un producto por su ID
    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }
}
