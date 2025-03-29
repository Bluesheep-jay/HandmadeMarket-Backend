package com.handmadeMarket.Shop;

import com.handmadeMarket.Exception.DuplicateResourcesException;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Mapper.ShopMapper;
import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.ProductService;
import com.handmadeMarket.Shop.dto.CreateShopDto;
import com.handmadeMarket.Users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final UserService userService;
    private final ProductService productService;

    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper,
                       UserService userService, ProductService productService) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
        this.userService = userService;
        this.productService = productService;
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop getShopById(String id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("shop not found with id " + id));
    }

    public List<Shop> getByIdList(List<String> idList) {
        return shopRepository.findByIdIn(idList);
    }

    public Shop addShop(CreateShopDto shopDto) {
        Shop newShop = shopMapper.toShop(shopDto);
        Shop createdShop = shopRepository.save(newShop);
        userService.addShopId(shopDto.getUserId(), createdShop.getId());
        return createdShop;
    }

    public Shop updateActiveStatus(String id, boolean isActive) {
        Optional<Shop> optionalShop = shopRepository.findById(id);
        if (optionalShop.isPresent()) {
            Shop shopToUpdate = optionalShop.get();
            shopToUpdate.setShopIsActive(isActive);
            return shopRepository.save(shopToUpdate);
        } else {
            throw new ResourceNotFoundException("shop not found with id " + id);
        }
    }

    public Shop updateInfo(String id, Shop updatedshop) {
        shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("shop not found with id " + id)
        );
        Shop shop = new Shop(updatedshop);
        return shopRepository.save(shop);
    }

//    public Shop updateShopRating(String id, double rating) {
//        Optional<Shop> optionalShop = shopRepository.findById(id);
//        if (optionalShop.isPresent()) {
//            Shop shopToUpdate = optionalShop.get();
//            shopToUpdate.setShopRating(rating);
//            return shopRepository.save(shopToUpdate);
//        } else {
//            throw new ResourceNotFoundException("shop not found with id " + id);
//        }
//    }

    ///  ========== PRODUCT ===========
    public List<Product> getProductsByShopId(String shopId) {
        Shop existingShop = getShopById(shopId);
        return productService.getByProductIdList(existingShop.getProductIdList());
    }

    @Transactional
    public Shop updateProductList(Product product) {
        Product createdProduct = productService.create(product);
        Optional<Shop> optionalShop = shopRepository.findById(product.getShopId());
        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();
            String productId = createdProduct.getId();
            if (!shop.getProductIdList().contains(productId)) {
                shop.getProductIdList().add(productId);
            } else {
                throw new DuplicateResourcesException("product with id " + productId + " already exists in shop");
            }
            return shopRepository.save(shop);
        } else {
            throw new ResourceNotFoundException("shop not found with id " + product.getShopId());
        }
    }

    @Transactional
    public void deleteProductIdFromProductList(Product product) {
        Optional<Shop> optionalShop = shopRepository.findById(product.getShopId());
        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();
            String productId = product.getId();
            if (shop.getProductIdList().contains(productId)) {
                shop.getProductIdList().remove(productId);
            } else {
                throw new ResourceNotFoundException("product with id " + productId + " does not exist in shop");
            }
            shopRepository.save(shop);
        } else {
            throw new ResourceNotFoundException("shop not found with id " + product.getShopId());
        }
    }

    public void deleteShop(String id) {
        shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("shop not found with id " + id)
        );
        shopRepository.deleteById(id);
    }

    public Shop deleteProduct(String id) {
        Product deletedProduct = productService.delete(id);
        Optional<Shop> optionalShop = shopRepository.findById(deletedProduct.getShopId());

        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();
            String productId = deletedProduct.getId();
            if (shop.getProductIdList().contains(productId)) {
                shop.getProductIdList().remove(productId);
            } else {
                throw new ResourceNotFoundException("product with id " + productId + " does not exist in shop");
            }
            return shopRepository.save(shop);
        } else {
            throw new ResourceNotFoundException("shop not found with id " + deletedProduct.getShopId());
        }
    }
}
