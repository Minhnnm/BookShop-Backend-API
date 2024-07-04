package com.example.bookshopapi.service.impl;

import com.cloudinary.Cloudinary;
import com.example.bookshopapi.config.jwt.JwtUtil;
import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.user.LoginResponse;
import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.Cart;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.entity.WishList;
import com.example.bookshopapi.exception.BadRequestException;
import com.example.bookshopapi.exception.ExistedException;
import com.example.bookshopapi.exception.NotFoundException;
import com.example.bookshopapi.mapper.UserMapper;
import com.example.bookshopapi.repository.CartRepository;
import com.example.bookshopapi.repository.RoleRepository;
import com.example.bookshopapi.repository.UserRepository;
import com.example.bookshopapi.repository.WishListRepository;
import com.example.bookshopapi.service.UserService;
import com.example.bookshopapi.util.CurrentUserUtil;
import com.example.bookshopapi.util.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final Cloudinary cloudinary;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto register(UserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ExistedException("USR_01", "Email đã tồn tại", "email");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        WishList wishList = new WishList();
        Cart cart = new Cart();
        user.setAvatar("");
        user.setCreatedDate(LocalDate.now());
        user.setRole(roleRepository.getById(2));
//        user.setStatus("active");
//        String accessToken = jwtUtil.generateToken(customer);
        userRepository.save(user);
        wishList.setUser(user);
        wishListRepository.save(wishList);
        cart.setUser(user);
        cartRepository.save(cart);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public LoginResponse login(UserRequestDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("Email không tồn tài trong hệ thống!");
        }
        if (user.getStatus() != 1) {
            throw new BadRequestException("USR_03", "Tài khoản đã bị khóa", "");
        }
        if (!bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("USR_02", "Sai tên tài khoản hoặc mật khẩu", "");
        }
        String accessToken = jwtUtil.generateToken(user);
        LocalDateTime expiredTime = jwtUtil.extractExpiration(accessToken);
        long expiresIn = ChronoUnit.HOURS.between(LocalDateTime.now().with(ChronoField.MILLI_OF_SECOND, 0), expiredTime);
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken("Bearer " + accessToken)
                .userDto(userMapper.toDto(user))
                .expires_in(expiresIn + "hours").build();
        return loginResponse;
    }

    @Override
    public MessageDto changePassword(String oldPassword, String newPassword) {
        User currentUser = currentUserUtil.getCurrentUser();
        if (!bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new BadRequestException("USR_04", "Mật khẩu cũ không chính xác!", "password");
        }
        currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        currentUser.setUpdatedDate(LocalDate.now());
        userRepository.save(currentUser);
        return new MessageDto("Đã thay đổi mật khẩu thành công!");
    }

    @Override
    public MessageDto changeAvatar(MultipartFile multiPartFile) {
        String imageUrl = uploadImage(multiPartFile, "user");
        User currentUser = currentUserUtil.getCurrentUser();
        currentUser.setAvatar(imageUrl.replace("http", "https"));
        currentUser.setUpdatedDate(LocalDate.now());
        userRepository.save(currentUser);
        return new MessageDto("Đã thay đổi avatar thành công!");
    }

    @Override
    public UserDto getProfile() {
        return userMapper.toDto(currentUserUtil.getCurrentUser());
    }

    @Override
    public MessageDto updateUser(UserDto userDto) {
        User currentUser = currentUserUtil.getCurrentUser();
        currentUser.setName(userDto.getName());
        currentUser.setAddress(userDto.getAddress());
        currentUser.setMobPhone(userDto.getMobPhone());
        currentUser.setGender(userDto.getGender());
        currentUser.setDateOfBirth(userDto.getDateOfBirth());
        currentUser.setUpdatedDate(LocalDate.now());
        userRepository.save(currentUser);
        return new MessageDto("Cập nhật thông tin thành công!");
    }

    @Override
    public List<UserDto> findAll(String query, int status, String typeAccount, String sortBy, String sortDir, int page, int limit) {
        Sort sort = Sort.by(sortBy);
        sort = "desc".equalsIgnoreCase(sortDir) ? sort.descending() : sort.ascending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        List<User> users = userRepository.findAll(query, status, typeAccount, pageRequest).getContent();
        return userMapper.toDtos(users);
    }

    @Override
    public MessageDto deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("USR_05", "Can not find user by id: " + userId, "id")
        );
        user.setStatus(UserStatus.DELETED.getValue());
        user.setUpdatedDate(LocalDate.now());
        userRepository.save(user);
        return new MessageDto("Đã xoá tài khoản khách hàng!");
    }

    @Override
    public MessageDto updateStatus(int userId, int status) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("USR_05", "Can not find user by id: " + userId, "id")
        );
        user.setStatus(status);
        user.setUpdatedDate(LocalDate.now());
        userRepository.save(user);
        return status==UserStatus.ACTIVE.getValue() ? new MessageDto("Đã mở khóa tài khoản khách hàng!") : new MessageDto("Đã khóa tài khoản khách hàng!");
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toUpperCase())));
    }

    public String uploadImage(MultipartFile multiPartFile, String folderName) {
        try {
            Map<String, Object> uploadParams = new HashMap<>();
            uploadParams.put("public_id", UUID.randomUUID().toString());
            uploadParams.put("folder", folderName);
            return cloudinary.uploader()
                    .upload(multiPartFile.getBytes(), uploadParams)
                    .get("url")
                    .toString();
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
