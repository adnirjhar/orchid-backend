package com.orchid.server.account;

import com.orchid.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPARepository extends JpaRepository<User, Long> {

    public User findOneByEmail(String username);
}
