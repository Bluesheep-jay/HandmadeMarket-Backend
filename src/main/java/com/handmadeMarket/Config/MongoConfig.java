package com.handmadeMarket.Config;

import com.handmadeMarket.Category.Category;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Configuration
@EnableMongoRepositories(basePackages = "com.handmadeMarket")
@EnableMongoAuditing
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
        mongoTemplate.indexOps(Category.class).ensureIndex(textIndex);
    }
}