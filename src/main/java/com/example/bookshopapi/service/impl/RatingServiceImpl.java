package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.rating.RatingRequestDto;
import com.example.bookshopapi.entity.Order;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.entity.Rating;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.exception.NotFoundException;
import com.example.bookshopapi.repository.OrderRepository;
import com.example.bookshopapi.repository.ProductRepository;
import com.example.bookshopapi.repository.RatingRepository;
import com.example.bookshopapi.service.RatingService;
import com.example.bookshopapi.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    public Page<Rating> findAllByProductId(UUID bookId, int limit, int page) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ratingRepository.findAllByProductIdOrderByCreateTimeDesc(bookId, pageable);
    }

    public double ratingLevel(UUID productId) {
        int totalRating = 0;
        List<Rating> ratings = ratingRepository.findAllByProductId(productId);
        if (ratings.size() > 0) {
            for (Rating rating : ratings) {
                totalRating += rating.getRatingLevel();
            }
            return Math.round(totalRating * 10.0 / ratings.size()) / 10.0;
        }
        return 5.0;
    }

    public MessageDto createRating(List<RatingRequestDto> ratingRequests) {
        for (RatingRequestDto ratingRequest : ratingRequests) {
            Rating rating = new Rating();
            Product product = productRepository.findById(ratingRequest.getProductId()).orElseThrow(
                    () -> new NotFoundException("Can not find product with id: " + ratingRequest.getId())
            );
            User customer = currentUserUtil.getCurrentUser();
            Order order = orderRepository.findById(ratingRequest.getOrderId()).orElseThrow(
                    () -> new NotFoundException("Can not find product with id: " + ratingRequest.getOrderId())
            );
            rating.setRatingLevel(ratingRequest.getRatingLevel());
            rating.setProduct(product);
            rating.setComment(ratingRequest.getComment());
            rating.setUser(customer);
            rating.setCreateTime(new Date());
            order.setIsRating(1);
            orderRepository.save(order);
            ratingRepository.save(rating);
        }
        return new MessageDto("Đánh giá sản phẩm thành công!");
    }

    @Override
    public Page<Rating> findRatingByBookId(int limit, int page) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        User user = currentUserUtil.getCurrentUser();
        return ratingRepository.findAllByUserIdOrderByCreateTimeDesc(user.getId(), pageable);
    }
}
