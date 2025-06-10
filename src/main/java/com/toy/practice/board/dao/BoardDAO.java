package com.toy.practice.board.dao;

import com.toy.practice.board.model.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardDAO {

    @Select("SELECT b.*, m.name as member_name " +
            "FROM board b " +
            "LEFT JOIN member m ON b.member_id = m.id " +
            "ORDER BY b.created_at DESC " +
            "LIMIT #{limit} OFFSET #{offset}")
    List<Board> getBoards(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT COUNT(*) FROM board")
    long count();
}
