package com.handmadeMarket.ChangeStreamListener;

import com.handmadeMarket.Order.Order;
import com.handmadeMarket.Revenue.RevenueService;
import com.mongodb.client.MongoChangeStreamCursor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import com.mongodb.client.model.changestream.OperationType;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderChangeStreamListener {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RevenueService revenueService;

    @EventListener(ApplicationReadyEvent.class)
    public void startChangeStreamListener() {
        new Thread(() -> {
            MongoCollection<Document> collection = mongoTemplate.getCollection("order");
            MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor = (MongoChangeStreamCursor<ChangeStreamDocument<Document>>) collection.watch().fullDocument(FullDocument.UPDATE_LOOKUP).iterator();

            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> change = cursor.next();
                if (change.getFullDocument() == null) {
                    System.out.println("⚠️ Change stream detected an event but full document is null!");
                    continue;
                }
                Document order = change.getFullDocument();

//                System.out.println("Detected Change: " + order.toJson());

                if (change.getOperationType() == OperationType.INSERT) {
                    Order newOrder = mongoTemplate.getConverter().read(Order.class, order);
                    revenueService.updateMonthlyRevenue(newOrder);
                }
            }
        }).start();
    }
}
