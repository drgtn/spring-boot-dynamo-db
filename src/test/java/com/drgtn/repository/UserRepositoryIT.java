package com.drgtn.repository;

import com.drgtn.BaseIT;
import com.drgtn.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.drgtn.fixtures.UserFixture.aUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserRepositoryIT extends BaseIT {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveFindByEmail() {
        User aUser = aUser();
        userRepository.save(aUser);
        assertThat(userRepository.findByEmail(aUser.getEmail())).isPresent().get()
                .usingRecursiveComparison().ignoringFields("userId").isEqualTo(aUser);
        userRepository.remove(aUser.getEmail());
        assertThat(userRepository.findByEmail(aUser.getEmail())).isNotPresent();
    }
}
