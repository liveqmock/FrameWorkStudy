package com.ps.custom.service;

import com.ps.custom.entity.main.User;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface UserService {
    User get(Long id);

    void saveOrUpdate(User user);

    void delete(Long id);

    List<User> findAll(Page page);

    List<User> findByExample(Specification<User> specification, Page page);

    void updatePwd(User user, String newPwd);

    void resetPwd(User user, String newPwd);

    User getByUsername(String username);
}
