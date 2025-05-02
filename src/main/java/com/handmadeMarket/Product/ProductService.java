package com.handmadeMarket.Product;

import com.handmadeMarket.Category.CategoryService;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Review.Review;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final MongoTemplate mongoTemplate;
    private final String PENDING_STATUS = "67ff79798226bac72b581e18";
    private final String COMPLETED_STATUS = "67d8cd2a347ab249ebe8b15b";

    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService,
                          MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
        this.categoryService = categoryService;
    }


    public long getTotalProduct() {
        return productRepository.count();
    }

    public static String removeDiacritics(String input) {
        if (input == null) return null;

        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("đ", "d")
                .replaceAll("Đ", "D");
    }


    @Transactional
    public Product create(Product product) {
        product.setProductStatusId(PENDING_STATUS);
        String unsignedTitle = removeDiacritics(product.getProductTitle());
        product.setProductTitleUnsigned(unsignedTitle.toLowerCase());

        return productRepository.save(product);
    }


    public List<Product> getProductsByRootCategoryId(String rootCategoryId) {
        List<String> allCategoryIds = new ArrayList<>(
                categoryService.getAllCategoryIdsByRootCategoryId(rootCategoryId));
        return productRepository.findByCategoryIdIn(allCategoryIds);
    }


        public static String removeAccent(String input) {
            if (input == null) return null;
            String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
            return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }

    public Set<Product> getProductBySearchText(String keywordBeforeRemoveAccent) {
        Set<Product> suggestions = new LinkedHashSet<>();

        // Step 1: Remove diacritics from the keyword
        String keyword = removeAccent(keywordBeforeRemoveAccent);

        // Step 2: Search for the full keyword
        Query fullKeywordQuery = new Query(
                Criteria.where("productTitleUnsigned").regex(".*" + keyword + ".*", "i")
        );
        List<Product> fullKeywordResults = mongoTemplate.find(fullKeywordQuery, Product.class);
        suggestions.addAll(fullKeywordResults);

        // Step 3: Search for sub-keywords (consecutive word combinations)
        String[] keywords = keyword.split("\\s+");
        List<Criteria> andConditions = new ArrayList<>();

        for (int i = 0; i < keywords.length; i++) {
            for (int j = i + 1; j <= keywords.length; j++) {
                String subKeyword = String.join(" ", Arrays.copyOfRange(keywords, i, j));
                andConditions.add(Criteria.where("productTitleUnsigned").regex(".*" + subKeyword + ".*", "i"));
            }
        }

        Query subKeywordQuery = new Query(new Criteria().andOperator(andConditions.toArray(new Criteria[0])));
        List<Product> subKeywordResults = mongoTemplate.find(subKeywordQuery, Product.class);
        suggestions.addAll(subKeywordResults);

        // Step 4: Search for individual words
        List<Criteria> orConditions = new ArrayList<>();
        for (String word : keywords) {
            orConditions.add(Criteria.where("productTitleUnsigned").regex(".*" + word + ".*", "i"));
        }

        Query singleWordQuery = new Query(new Criteria().orOperator(orConditions.toArray(new Criteria[0])));
        List<Product> singleWordResults = mongoTemplate.find(singleWordQuery, Product.class);
        suggestions.addAll(singleWordResults);

        return suggestions;
    }

    //    public List<Product> getBestSellingProductsForCurrentMonth() {
//        LocalDate now = LocalDate.now();
//        Instant startOfMonth = now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
//        Instant startOfNextMonth = now.plusMonths(1).withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
//
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("order_status_id").is(COMPLETED_STATUS)
//                        .and("order_date").gte(startOfMonth).lt(startOfNextMonth)),
//
//                Aggregation.unwind("order_details"),
//
//                Aggregation.group("order_details.productId")
//                        .sum("order_details.quantity").as("soldCount"),
//
//                Aggregation.sort(Sort.by(Sort.Direction.DESC, "soldCount")),
//
//                Aggregation.limit(20),
//
//                // Đúng lookup theo _id sau group
//                Aggregation.lookup("product", "_id", "_id", "productInfo"),
//
//                Aggregation.unwind("productInfo", true),
//
//                Aggregation.project()
//                        .and("productInfo._id").as("id")
//                        .and("productInfo.product_title").as("productTitle")
//                        .and("productInfo.category_id").as("categoryId")
//                        .and("productInfo.product_description").as("productDescription")
//                        .and("productInfo.personalization_description").as("personalizationDescription")
//                        .and("productInfo.image_list").as("imageList")
//                        .and("productInfo.video_url").as("videoUrl")
//                        .and("productInfo.base_price").as("basePrice")
//                        .and("productInfo.base_quantity").as("baseQuantity")
//                        .and("productInfo.variation_list").as("variationList")
//                        .and("productInfo.weight").as("weight")
//                        .and("productInfo.length").as("length")
//                        .and("productInfo.width").as("width")
//                        .and("productInfo.height").as("height")
//                        .and("productInfo.rating").as("rating")
//                        .and("productInfo.shop_id").as("shopId")
//                        .and("productInfo.product_status_id").as("productStatusId")
//                        .and("soldCount").as("soldCount")
//        );
//
//        AggregationResults<Product> results = mongoTemplate.aggregate(aggregation, "order", Product.class);
//        return results.getMappedResults();
//    }
    public List<Document> getBestSellingProductsForCurrentMonth() {
        LocalDate now = LocalDate.now();
        Instant startOfMonth = now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant startOfNextMonth = now.plusMonths(1).withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("order_status_id").is(COMPLETED_STATUS)
                        .and("order_date").gte(startOfMonth).lt(startOfNextMonth)),

                Aggregation.unwind("order_details", true),

                Aggregation.addFields()
                        .addField("productId")
                        .withValue(ConvertOperators.ToObjectId.toObjectId("$order_details.productId"))
                        .build(),

                Aggregation.lookup("product", "productId", "_id", "productInfo"),
//
                Aggregation.unwind("productInfo", true),
//
                Aggregation.group("$order_details.productId")
                        .first("$productInfo.product_title").as("product_title")
                        .first("$productInfo.product_description").as("product_description")
                        .first("$productInfo.category_id").as("category_id")
                        .first("$productInfo.image_list").as("image_list")
                        .first("$productInfo.base_price").as("base_price")
                        .first("$productInfo.base_quantity").as("base_quantity")
                        .first("$productInfo.variation_list").as("variation_list")
                        .first("$productInfo.rating").as("rating")
                        .first("$productInfo.shop_id").as("shop_id")
                        .first("$productInfo.sold_count").as("sold_count")
                        .sum("order_details.quantity").as("totalQuantitySold"),

                Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalQuantitySold")),

                Aggregation.limit(20)
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "order", Document.class);
        return results.getMappedResults();
    }


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with id: " + id)
        );
    }

    public List<Product> getByProductIdList(List<String> productIds) {
        return productRepository.findAllById(productIds);
    }

    public List<Product> getByIdList(List<String> idList) {
        return productRepository.findByIdIn(idList);
    }

    public Product update(String id, Product updatedProduct) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found"));
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    public Product updateRating(String id, int rating, List<Review> reviews) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found"));

        double newAverageRating;
        if (reviews.isEmpty()) {
            newAverageRating = rating;
        } else {
            double totalRating = reviews.stream().mapToInt(Review::getReviewRating).sum() + rating;
            newAverageRating = totalRating / (reviews.size() + 1);
        }
        product.setRating(newAverageRating);
        return productRepository.save(product);
    }

    public Product updateSoldCount(String id, int soldCount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found"));
        product.setSoldCount(product.getSoldCount() + soldCount);
        return productRepository.save(product);
    }

    public void decreaseBaseQuantity(String productId, int quantity) {
        Query query = new Query(Criteria.where("_id").is(productId));
        Update update = new Update().inc("base_quantity", -quantity);
        mongoTemplate.updateFirst(query, update, Product.class);
    }

    public void decreaseVariationStock(String productId, Map<String, String> selectedOptions, int quantity) {
        Product product = mongoTemplate.findById(productId, Product.class);
        if (product == null || product.getVariationList() == null) return;

        List<Variation> variations = product.getVariationList();
        for (int i = 0; i < variations.size(); i++) {
            Variation variation = variations.get(i);
            if (variation.getAttributes().equals(selectedOptions)) {
                int newStock = variation.getStock() - quantity;
                if (newStock < 0) newStock = 0;

                // Cập nhật lại chỉ phần tử variation cụ thể trong danh sách
                Query query = new Query(Criteria.where("_id").is(productId));
                Update update = new Update().set("variation_list." + i + ".stock", newStock);
                mongoTemplate.updateFirst(query, update, Product.class);
                break;
            }
        }
    }


    public Product delete(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found"));
        productRepository.deleteById(productId);
        return product;
    }


    public Product approveProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//        product.setApproved(true);
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

}
