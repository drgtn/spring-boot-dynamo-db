package com.drgtn.repository.impl;

import com.drgtn.model.User;
import com.drgtn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DynamoDbTable<User> userTable;

    public void save(User user) {
        userTable.putItem(user);
    }

    public void remove(String email) {
        findByEmail(email).ifPresent(userTable::deleteItem);
    }


    public Optional<User> findByEmail(String email) {
        User user = userTable.getItem(r -> r.key(k -> k.partitionValue(email)));
        return Optional.ofNullable(user);
    }
}
