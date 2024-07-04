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
import com.example.bookshopapi.mapper.ProductMapper;
import com.example.bookshopapi.repository.AuthorRepository;
import com.example.bookshopapi.repository.CategoryRepository;
import com.example.bookshopapi.repository.ProductRepository;
import com.example.bookshopapi.repository.SupplyRepository;
import com.example.bookshopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private ProductMapper productMapper;

    @Override
    public List<ProductDto> searchProduct(int page, int limit, String query, String sortBy, String sortDir, Integer categoryId,
                                          Integer authorId, Integer supplierId) {
        Sort sort = Sort.by(sortBy);
        sort = "desc".equalsIgnoreCase(sortDir) ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId).orElseThrow(
                () -> new BadRequestException("Can not find category with id : " + categoryId)
        );
        Author author = authorId == null ? null : authorRepository.findById(authorId).orElseThrow(
                () -> new BadRequestException("Can not find author with id : " + authorId)
        );
        Supplier supplier = supplierId == null ? null : supplyRepository.findById(supplierId).orElseThrow(
                () -> new BadRequestException("Can not find category with id : " + supplierId)
        );
        List<Product> products = productRepository.searchProduct(query, category, author, supplier, pageRequest).getContent();
        return productMapper.toDtos(products);
    }

    @Override
    public List<ProductDto> findByCategory(Integer categoryId) {
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId).orElseThrow(
                () -> new BadRequestException("Can not find category with id : " + categoryId)
        );
        List<Product> products = productRepository.findByCategory(category);
        return productMapper.toDtos(products);
    }

    @Override
    public List<ProductDto> findBySupplier(Integer supplierId) {
        Supplier supplier = supplierId == null ? null : supplyRepository.findById(supplierId).orElseThrow(
                () -> new BadRequestException("Can not find supplier with id: " + supplierId)
        );
        List<Product> products = productRepository.findBySupplier(supplier);
        return productMapper.toDtos(products);
    }

    @Override
    public List<ProductDto> findByAuthor(Integer authorId) {
        Author author = authorId == null ? null : authorRepository.findById(authorId).orElseThrow(
                () -> new BadRequestException("Can nont find author with id: " + authorId)
        );
        List<Product> products = productRepository.findByAuthor(author);
        return productMapper.toDtos(products);
    }

    @Override
    public ProductDto findById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Can not find product with id: " + id)
        );
        return productMapper.toDto(product);
    }

    @Override
    public MessageDto create(ProductRequestDto productRequest) {
        Optional<Product> product = productRepository.findByName(productRequest.getName());
        if(product.isPresent()){
            throw new ExistedException("Sản phẩm này đã tồn tại trong hệ thống");
        }
//        MultipartFile multipartFile = new MultiPartFile().createMultipartFileFromUrl(productRequest.getImage(), productRequest.getFileName());
//        String imageURL = customerService.uploadFile(multipartFile, "product");
//        Product = new BookUtil().setBookFromRequest(bookRequest);
//        book.setImage(imageURL.replace("http", "https"));
//        book.setThumbnail(imageURL.replace("http", "https"));
//        if (bookRequest.getIsBannerSelected()) {
//            book.setBanner(imageURL.replace("http", "https"));
//        }
//        productService.addBook(book);
        return new MessageDto("Đã thêm sản phẩm thành công!");
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
