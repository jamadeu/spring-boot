package com.jamadeu.repository;

import com.jamadeu.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Jean Amadeu 07/13/2020
 */
public interface IUserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
