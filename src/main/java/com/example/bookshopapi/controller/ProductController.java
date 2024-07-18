package com.example.bookshopapi.controller;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.product.ProductRequestDto;
import com.example.bookshopapi.dto.rating.RatingRequestDto;
import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.Rating;
import com.example.bookshopapi.service.ProductService;
import com.example.bookshopapi.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/all")
    public ResponseEntity<?> searchProduct(@RequestParam(name = "limit", defaultValue = "10") int limit,
                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "query_string", defaultValue = "") String queryString,
                                           @RequestParam(name = "sort_by", required = false, defaultValue = "createdDate") String sortBy,
                                           @RequestParam(name = "sort_dir", required = false, defaultValue = "asc") String sortDir,
                                           @RequestParam(name = "category_id", required = false) Integer categoryId,
                                           @RequestParam(name = "supply_id", required = false) Integer supplyId,
                                           @RequestParam(name = "author_id", required = false) Integer authorId) {
        return ResponseEntity.ok(productService.searchProduct(page, limit, queryString, sortBy, sortDir, categoryId, authorId, supplyId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<?> getProductsByAuthor(@PathVariable Integer authorId) {
        return ResponseEntity.ok(productService.findByAuthor(authorId));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<?> getProductsBySupplier(@PathVariable Integer supplierId) {
        return ResponseEntity.ok(productService.findBySupplier(supplierId));
    }

    @GetMapping("findById/{productId}")
    public ResponseEntity<?> findById(@PathVariable("productId") UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(
            @RequestPart ProductRequestDto product,
            @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.create(product, file));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> delete(@PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(productService.delete(productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestPart(required = false) ProductRequestDto product,
                                    @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.update(product, file));
    }

    @GetMapping("/rating")
    public ResponseEntity<?> findRatingByBook(@RequestParam("product_id") UUID productId,
                                              @RequestParam("limit") int limit,
                                              @RequestParam("page") int page) {
        Page<Rating> ratings = ratingService.findAllByProductId(productId, limit, page);
        return ResponseEntity.ok(ratings.getContent());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/rating/create")
    public ResponseEntity<?> createRatingOrder(@RequestBody List<RatingRequestDto> ratingRequests) {
        ratingService.createRating(ratingRequests);
        return ResponseEntity.ok(new MessageDto("Đánh giá sản phẩm thành công!"));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/rating_by_user")
    public ResponseEntity<?> getAllRatingByUser(@RequestParam int limit,
                                                @RequestParam int page) {
        Page<Rating> ratings = ratingService.findRatingByBookId(limit, page);
        return ResponseEntity.ok(ratings.getContent());
    }
}
