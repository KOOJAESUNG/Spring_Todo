package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//퍼시스턴스의 역할을 한다.
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity,String> { //id의 타입인 String을 넣어준다.
    List<TodoEntity> findByUserId(String userId);
}
