package com.handmadeMarket.Config;

import com.handmadeMarket.Category.CategoryLevel2;
import com.handmadeMarket.Product.Product;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.handmadeMarket")
public class MongoConfig extends AbstractMongoClientConfiguration{

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return "JavaHandmadeMarket";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @PostConstruct
    public void initializeIndexesInDb(){
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), getDatabaseName());

        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("category_level_2_name")
                .build();
        mongoTemplate.indexOps(CategoryLevel2.class).ensureIndex(textIndex);


    }
}