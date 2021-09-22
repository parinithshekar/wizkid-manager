package dev.owow.wizkidmanager2000.repository;

import dev.owow.wizkidmanager2000.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT CASE WHEN (COUNT(u)>0) THEN true ELSE false END "+
    "FROM users u WHERE u.email=:email AND u.status='FIRED'")
    Boolean isFired(@Param("email") String email);

    @Query(value = "SELECT u FROM users u WHERE u.firstName LIKE %:search% OR u.lastName LIKE %:search%")
    List<UserEntity> search(@Param("search") String search);

    @Query(value = "SELECT u FROM users u WHERE (u.firstName LIKE %:search% OR u.lastName LIKE %:search%) AND u.role=:role")
    List<UserEntity> searchWithRole(@Param("search") String search, @Param("role") String role);
}
