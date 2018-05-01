package com.sda.springbootdemo.exercises.service;

import com.sda.springbootdemo.exercises.exception.NotFoundException;
import com.sda.springbootdemo.exercises.exception.ValidationException;
import com.sda.springbootdemo.exercises.model.Product;
import com.sda.springbootdemo.exercises.model.Receipt;
import com.sda.springbootdemo.exercises.repository.ProductRepository;
import com.sda.springbootdemo.exercises.repository.ReceiptRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Creates {@link Product} using repository and validates if name is unique.
     * If validation fails {@link ValidationException} will be thrown.
     *
     * @param product product to be saved
     * @return saved product returned from repository
     */
    public Product create(Product product) {
        validateProductName(product.getName(), null);
        return productRepository.save(product);
    }

    /**
     * Retrieves {@link Product} by id, if there is none with given id
     * {@link NotFoundException} will be thrown.
     *
     * @param id product id that will be retrieved
     * @return found product
     */
    public Product get(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Product with id %s not found", id)));
    }

    /**
     * Searches for {@link Product} saved in data base.
     *
     * @param name used for searching products containing given param ignoring case
     * @param minPrice used for searching products with prices greater equal than given value
     * @param maxPrice used for searching products with prices lesser equal than given value
     * @param pageable contains sorting and paging parameters
     * @return found products matching given parameters
     */
    public Page<Product> search(String name, Double minPrice, Double maxPrice, Pageable pageable) {
        if (null == minPrice) {
            minPrice = 0d;
        }
        if (null == maxPrice) {
            maxPrice = Double.MAX_VALUE;
        }
        return productRepository
            .findByNameIgnoreCaseContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(
                name, minPrice, maxPrice, pageable);
    }

    /**
     * Removes {@link Product} with given id, if there is none with given id
     * {@link NotFoundException} will be thrown.
     *
     * @param id product id that will be removed
     */
    public void remove(Long id) {
        // we are using get method here to avoid code duplication
        productRepository.delete(get(id));
    }

    /**
     * Updates {@link Product} retrieved using id parameter,
     * if none found {@link NotFoundException} will be thrown.
     * Validates uniqueness of name, if fails {@link ValidationException} will be thrown.
     *
     * @param product product body to be updated
     * @param id product id that will be updated
     * @return saved product returned from repository
     */
    public Product update(Product product, Long id) {
        // we are using get method here to avoid code duplication
        Product existingProduct = get(id);

        validateProductName(product.getName(), existingProduct.getName());

        product.setId(id);
        return productRepository.save(product);
    }

    // validates name uniqueness,
    // current name is for the case we are updating product, but not changing the name,
    // for create current name should be null
    private void validateProductName(String name, String currentName) {
        if (null == name) {
            throw new ValidationException("Product name is required");
        }

        if (productRepository.existsByName(name) && !name.equals(currentName)) {
            throw new ValidationException(String.format("Product name already exists %s", name));
        }
    }
}
