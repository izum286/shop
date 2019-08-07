package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.controller.dto.*;
import com.telran.telranshopspringdata.data.*;
import com.telran.telranshopspringdata.data.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.telran.telranshopspringdata.service.Mapper.map;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    ProductStatisticRepository productStatisticRepository;

    @Autowired
    UserStatisticRepository userStatisticRepository;


    @Override
    //@Transactional
    public Optional<UserDto> addUserInfo(String email, String name, String phone) {
        if(!userRepository.existsById(email)){
            UserEntity entity = new UserEntity(email, name, phone, BigDecimal.valueOf(0), null, null, null);
            userRepository.save(entity);
            UserDetailsEntity detailsEntity = userDetailsRepository.findById(email).orElseThrow();
            detailsEntity.setRoles(
                    List.of(
                            UserRoleEntity.builder()
                                    .role("ROLE_FULL_USER")
                                    .build()
                    )
            );
            userDetailsRepository.save(detailsEntity);
            return Optional.of(map(entity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> getUserInfo(String email) {
        UserEntity entity = userRepository.findById(email).orElseThrow();
        return Optional.of(map(entity));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAllBy()
                .map(Mapper::map)
                .collect(Collectors.toList());

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAllBy()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryId) {
        return productRepository.findAllByCategory_Id(categoryId)
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShoppingCartDto> addProductToCart(String userEmail, String productId, int count) {
        ShoppingCartEntity entity = shoppingCartRepository.findByOwner_Email(userEmail);
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();
        if (entity != null ) {
            ProductOrderEntity poe = entity.getProducts().stream()
                    .filter(p -> p.getProductId().equals(productId))
                    .findAny()
                    .orElse(null);
            if (poe == null) {
                poe = new ProductOrderEntity();
                poe.setProductId(productEntity.getId());
                poe.setCategory(productEntity.getCategory());
                poe.setName(productEntity.getName());
                poe.setPrice(productEntity.getPrice());
                poe.setCount(count);
                productOrderRepository.save(poe);
                poe.setShoppingCart(entity);
                entity.getProducts().add(poe);
                shoppingCartRepository.save(entity);
            }else{
                poe.setCount(poe.getCount()+count);
            }
            return Optional.of(map(entity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShoppingCartDto> removeProductFromCart(String userEmail, String productId, int count) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByOwner_Email(userEmail);
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();
        if (shoppingCartEntity.getProducts() != null) {
            ProductOrderEntity productOrderEntity =shoppingCartEntity.getProducts().stream().filter(p -> p.getProductId().equals(productId)).findAny()
            .orElse(null);
            if (productOrderEntity != null) {
                int productCount = productOrderEntity.getCount();
                if (productCount > count) {
                    productOrderEntity.setCount(productCount-count);
                }else {
                    shoppingCartEntity.getProducts().remove(productOrderEntity);
                }
                shoppingCartRepository.save(shoppingCartEntity);
            }else {
                throw new RuntimeException("No such product in cart");
            }
            return Optional.of(map(shoppingCartEntity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShoppingCartDto> getShoppingCart(String userEmail) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByOwner_Email(userEmail);
        if (shoppingCartEntity == null) {
            throw new RuntimeException("Wrong Shopping Cart");
        }
        return Optional.of(map(shoppingCartRepository.findByOwner_Email(userEmail)));
    }

    @Override
    public boolean clearShoppingCart(String userEmail) {
        ShoppingCartEntity toClean = shoppingCartRepository.findByOwner_Email(userEmail);
        if (toClean != null) {
            toClean.setProducts(List.of());
            toClean.setDate(null);
            shoppingCartRepository.save(toClean);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDto> getOrders(String userEmail) {
        return orderRepository.getAllByOwnerEmail(userEmail)
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> checkout(String userEmail) {
        if (enoughMoney(userEmail)) {
            ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByOwner_Email(userEmail);
            UserEntity userEntity = userRepository.findById(userEmail).orElseThrow();
            OrderEntity orderEntity = new OrderEntity();
            List<ProductOrderEntity> products = shoppingCartEntity.getProducts();
            for (ProductOrderEntity productOrderEntity : products) {
                productOrderEntity.setOrder(orderEntity);
                productOrderEntity.setShoppingCart(null);
            }
            orderEntity.setOwner(userEntity);
            orderEntity.setProducts(shoppingCartEntity.getProducts());
            orderEntity.setStatus(OrderStatus.DONE);
            orderRepository.save(orderEntity);
            clearShoppingCart(userEmail);
            userEntity.getOrders().add(orderEntity);
            userEntity.setBalance(userEntity.getBalance().subtract(totalCost(userEmail)));
            userRepository.save(userEntity);

            //=====filling product stat===NOT_FINISHED
            //I didnt have enough time to finish
            for (ProductOrderEntity productOrderEntity : products) {
                //if product id not exist in row = add row and fill
                //if exist - update row
            }

            //======filling user stat
            UserStatisticEntity userStatisticEntity = userStatisticRepository.findById(userEmail).orElse(null);
            if(userStatisticEntity==null){
                 userStatisticEntity = UserStatisticEntity.builder()
                        .userEmail(userEntity.getEmail())
                        .TotalProductCount(products.size())
                        .totalAmount(totalCost(userEntity.getEmail())).build();
            }else {
                userStatisticEntity.setTotalProductCount(userStatisticEntity.getTotalProductCount()+products.size());
                userStatisticEntity.setTotalAmount(userStatisticEntity.getTotalAmount().add(totalCost(userEmail)));
            }

            userStatisticRepository.save(userStatisticEntity);



            return Optional.of(map(orderEntity));
        }
        throw new RuntimeException("Not enough money");
    }

    private boolean enoughMoney (String userEmail){
        ShoppingCartEntity toCheck = shoppingCartRepository.findByOwner_Email(userEmail);
        UserEntity userEntity = userRepository.findById(userEmail).orElseThrow();

        if (toCheck != null) {
            long totalCost = toCheck.getProducts().stream().map(p -> (p.getPrice().longValue() * p.getCount())).count();
            boolean isEnough = userEntity.getBalance().longValue() > totalCost ? true : false;
            return isEnough;
        }
        throw new RuntimeException("Shopping cart is empty yet");
    }

    private BigDecimal totalCost ( String userEmail){
        ShoppingCartEntity toCheck = shoppingCartRepository.findByOwner_Email(userEmail);
        BigDecimal b = new BigDecimal(toCheck.getProducts().stream().map(p -> (p.getPrice().longValue() * p.getCount())).count());
        return b;
    }
}
