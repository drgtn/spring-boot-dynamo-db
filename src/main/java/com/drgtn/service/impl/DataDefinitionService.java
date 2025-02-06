package com.drgtn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataDefinitionService {
    private final DynamoDbClient dynamoDbClient;

    public void createUserTable() {
        boolean tableExists = dynamoDbClient.listTables().tableNames().contains(com.drgtn.model.User.USER);
        if (!tableExists) {
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(com.drgtn.model.User.USER)
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("email")
                            .keyType(KeyType.HASH)
                            .build())
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("email")
                            .attributeType(ScalarAttributeType.S)
                            .build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();

            dynamoDbClient.createTable(request);
            log.info("{} table created!", com.drgtn.model.User.USER);
        }
    }
}
