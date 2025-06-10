package com.toy.practice.category.mapper;

import com.toy.practice.category.dto.BoardCategoryResponse;
import com.toy.practice.category.model.BoardCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardCategoryMapper {
    BoardCategoryMapper INSTANCE = Mappers.getMapper(BoardCategoryMapper.class);
    BoardCategoryResponse toDTO(BoardCategory boardCategory);
    List<BoardCategoryResponse> toDTOList(List<BoardCategory> list);
}
