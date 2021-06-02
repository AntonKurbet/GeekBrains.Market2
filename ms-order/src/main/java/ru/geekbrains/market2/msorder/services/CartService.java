package ru.geekbrains.market2.msorder.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.geekbrains.market2.msorder.model.entities.Cart;
import ru.geekbrains.market2.msorder.model.entities.CartItem;
import ru.geekbrains.market2.msorder.repositories.CartRepository;
import ru.geekbrains.market2.routing.clients.ProductClient;
import ru.geekbrains.market2.routing.dtos.CartDto;
import ru.geekbrains.market2.routing.dtos.ProductDto;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductClient productClient;

    private final ModelMapper modelMapper;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public CartDto findById(UUID id) {
        return modelMapper.map(cartRepository.findById(id).get(), CartDto.class);
    }

    @Transactional
    public void addToCart(UUID cartId, Long productId) {
        CartDto cartDto = findById(cartId);
        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.setId(cartId);
        CartItem cartItem = cart.getItemByProductId(productId);
        if (cartItem != null) {
            cartItem.incrementQuantity();
            cart.recalculate();
            cartRepository.save(cart);
            return;
        }
        ProductDto p = productClient.findProductById(productId);
        cart.add(new CartItem(p));
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(UUID cartId) {
        CartDto cartDto = findById(cartId);
        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.clear();
    }

    public Optional<Cart> findByUserId(Long id) {
        return cartRepository.findByUserId(id);
    }

    @Transactional
    public UUID getCartForUser(Long userId, UUID cartUuid) {
        if (userId != null && cartUuid != null) {
            CartDto cartDto = findById(cartUuid);
            Cart cart = modelMapper.map(cartDto, Cart.class);
            Optional<Cart> oldCart = findByUserId(userId);
            if (oldCart.isPresent()) {
                cart.merge(oldCart.get());
                cartRepository.delete(oldCart.get());
            }
            cart.setUserId(userId);
        }
        if (userId == null) {
            Cart cart = save(new Cart());
            return cart.getId();
        }
        Optional<Cart> cart = findByUserId(userId);
        if (cart.isPresent()) {
            return cart.get().getId();
        }
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        save(newCart);
        return newCart.getId();
    }
}
