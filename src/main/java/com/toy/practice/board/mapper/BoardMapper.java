package com.toy.practice.board.mapper;

import com.toy.practice.board.dto.BoardCreateRequest;
import com.toy.practice.board.dto.BoardResponse;
import com.toy.practice.board.model.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    BoardMapper INSTANCE  = Mappers.getMapper(BoardMapper.class);

    public List<BoardResponse> toDTOList(List<Board> list);
    public Board toEntityFromCreate(BoardCreateRequest request);
}
