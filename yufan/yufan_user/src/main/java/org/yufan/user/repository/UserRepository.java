package org.yufan.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.yufan.user.bean.User;

public interface UserRepository extends CrudRepository<User,Integer> {

    public User findUserByUsernameAndPassword(String username, String password);

    public User findUserByUsername(String username);

    public User findUserByEmail(String email);

    public User findUserByPhone(String phone);

}
