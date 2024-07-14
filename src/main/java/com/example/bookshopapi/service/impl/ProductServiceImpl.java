package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.product.ProductDto;
import com.example.bookshopapi.dto.product.ProductRequestDto;
import com.example.bookshopapi.entity.Author;
import com.example.bookshopapi.entity.Category;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.entity.Supplier;
import com.example.bookshopapi.exception.BadRequestException;
import com.example.bookshopapi.exception.ExistedException;
import com.example.bookshopapi.exception.NotFoundException;
import com.example.bookshopapi.mapper.ProductMapper;
import com.example.bookshopapi.repository.AuthorRepository;
import com.example.bookshopapi.repository.CategoryRepository;
import com.example.bookshopapi.repository.ProductRepository;
import com.example.bookshopapi.repository.SupplyRepository;
import com.example.bookshopapi.service.CloudinaryService;
import com.example.bookshopapi.service.ProductService;
import com.example.bookshopapi.util.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplyRepository supplyRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDto> searchProduct(int page, int limit, String query, String sortBy, String sortDir, Integer categoryId,
                                          Integer authorId, Integer supplierId) {
        Sort sort = Sort.by(sortBy);
        sort = "desc".equalsIgnoreCase(sortDir) ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Can not find category with id : " + categoryId)
        );
        Author author = authorId == null ? null : authorRepository.findById(authorId).orElseThrow(
                () -> new NotFoundException("Can not find author with id : " + authorId)
        );
        Supplier supplier = supplierId == null ? null : supplyRepository.findById(supplierId).orElseThrow(
                () -> new NotFoundException("Can not find category with id : " + supplierId)
        );
        List<Product> products = productRepository.searchProduct(query, category, author, supplier, pageRequest).getContent();
        return productMapper.toDtos(products);
    }

    @Override
    public List<ProductDto> findByCategory(Integer categoryId) {
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Can not find category with id : " + categoryId)
        );
        List<Product> products = productRepository.findByCategory(category);
        return productMapper.toDtos(products);
    }

    @Override
    public List<ProductDto> findBySupplier(Integer supplierId) {
        Supplier supplier = supplierId == null ? null : supplyRepository.findById(supplierId).orElseThrow(
                () -> new NotFoundException("Can not find supplier with id: " + supplierId)
        );
        List<Product> products = productRepository.findBySupplier(supplier);
        return productMapper.toDtos(products);
    }

    @Override
    public List<ProductDto> findByAuthor(Integer authorId) {
        Author author = authorId == null ? null : authorRepository.findById(authorId).orElseThrow(
                () -> new NotFoundException("Can not find author with id: " + authorId)
        );
        List<Product> products = productRepository.findByAuthor(author);
        return productMapper.toDtos(products);
    }

    @Override
    public ProductDto findById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Can not find product with id: " + id)
        );
        return productMapper.toDto(product);
    }

    @Override
    public MessageDto create(ProductRequestDto productRequest, MultipartFile file) {
        Optional<Product> existedProduct = productRepository.findByName(productRequest.getName());
        if (existedProduct.isPresent()) {
            throw new ExistedException("Sản phẩm này đã tồn tại trong hệ thống");
        }
//        Product product = new Product();
        String imageUrl = cloudinaryService.uploadFile(file, "products")
                .replace("http", "https");
        Product product = productMapper.toEntity(productRequest);
        product.setImage(imageUrl);
        if (productRequest.getIsBannerSelected()) {
            product.setBanner(imageUrl);
        }
        productRepository.save(product);
        return new MessageDto("Đã thêm sản phẩm thành công!");
    }

    @Override
    public MessageDto delete(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Can not find product with id: " + productId)
        );
        product.setStatus(UserStatus.DELETED.getValue());
        product.setUpdatedDate(LocalDateTime.now());
        productRepository.save(product);
        return new MessageDto("Đã xóa sản phẩm thành công!");
    }

    @Override
    public MessageDto update(ProductRequestDto productRequest, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productRequest.getId()).orElseThrow(
                () -> new NotFoundException("Can not find product with id: " + productRequest.getId())
        );
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setDiscountedPrice(productRequest.getDiscountedPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setAuthor(productRequest.getAuthor());
        product.setSupplier(productRequest.getSupplier());
        product.setCategory(productRequest.getCategory());
        product.setUpdatedDate(LocalDateTime.now());
        if (file.getBytes().length != 0) {
            String imageUrl = cloudinaryService.uploadFile(file, "products")
                    .replace("http", "https");
            product.setImage(imageUrl);
        }
        productRepository.save(product);
        return new MessageDto("Đã cập nhật sản phẩm thành công!");
    }
//    @Override
//    public Optional<Product> findById(int productId) {
//        return productRepository.findById(productId);
//    }
//
//    @Override
//    public Optional<Product> findByName(String productName) {
//        return productRepository.findByName(productName);
//    }
//
//    @Override
//    public List<Product> getProductsNew() {
//        return productRepository.findTop20ByOrderByIdDesc();
//    }
//
//    public List<Product> getProductsHot() {
//        return productRepository.findTop20ByOrderByQuantitySoldDesc();
//    }
//
//    public List<Product> getProductsBanner() {
//        return productRepository.findTop5ByBannerIsNotNullOrderByIdDesc();
//    }
//
//    public List<Product> getBookByQuantitySold() {
//        return productRepository.findTop5ByOrderByQuantitySoldDesc();
//    }
//
//    @Override
//    public Page<Product> findProductsByCategory(int categoryId, int descriptionLength, int page, int limit) {
//        Pageable pageable= PageRequest.of(page, limit);
//        return productRepository.findAllByCategoryIdAndDescriptionLengthGreaterThanEqual(categoryId, descriptionLength, pageable);
//    }
}
