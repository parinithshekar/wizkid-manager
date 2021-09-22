package dev.owow.wizkidmanager2000.repository;

import dev.owow.wizkidmanager2000.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT CASE WHEN (COUNT(u)>0) THEN true ELSE false END "+
    "FROM users u WHERE u.email=:email AND u.status='FIRED'")
    Boolean isFired(@Param("email") String email);

}
