package com.handmadeMarket.Shop;

import com.handmadeMarket.Exception.DuplicateResourcesException;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop getShopById(String id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("shop not found with id " + id));
    }

    public Shop addShop(Shop shop) {
        return shopRepository.save(shop);
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
        Optional<Shop> optionalShop = shopRepository.findById(id);
        if (optionalShop.isPresent()) {
            Shop shopToUpdate = optionalShop.get();
            shopToUpdate.setShopName(updatedshop.getShopName());
            shopToUpdate.setShopDescription(updatedshop.getShopDescription());
            return shopRepository.save(shopToUpdate);
        } else {
            throw new ResourceNotFoundException("shop not found with id " + id);
        }
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


    @Transactional
    public void updateProductList(Product product) {
        Optional<Shop> optionalShop = shopRepository.findById(product.getShopId());
        if (optionalShop.isPresent()) {
            Shop shop = optionalShop.get();
            String productId = product.getId();
            if (!shop.getProductIdList().contains(productId)) {
                shop.getProductIdList().add(productId);
            } else {
                throw new DuplicateResourcesException("product with id " + productId + " already exists in shop");
            }
            shopRepository.save(shop);
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
            }else {
                throw new ResourceNotFoundException("product with id " + productId + " does not exist in shop");
            }
            shopRepository.save(shop);
        }else {
            throw new ResourceNotFoundException("shop not found with id " + product.getShopId());
        }
    }

    public void deleteShop(String id) {
        shopRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("shop not found with id " + id)
        );
        shopRepository.deleteById(id);
    }
}
