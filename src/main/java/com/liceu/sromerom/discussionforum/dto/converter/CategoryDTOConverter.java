package com.liceu.sromerom.discussionforum.dto.converter;

import com.liceu.sromerom.discussionforum.dto.CategoryDTO;
import com.liceu.sromerom.discussionforum.entities.Category;
import com.liceu.sromerom.discussionforum.entities.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryDTOConverter {
    @Autowired
    ModelMapper modelMapper;

    public CategoryDTO convertToDTO(Category category) {
        modelMapper.addConverter(parseModerators);
        modelMapper.addConverter(toEmpty);
        return modelMapper.map(category, CategoryDTO.class);
    }

    Converter<String, String> toEmpty = new AbstractConverter<>() {
        protected String convert(String source) {
            return source == null ? "" : source;
        }
    };
    Converter<Set<User>, List<Long>> parseModerators = new AbstractConverter<>() {
        @Override
        protected List<Long> convert(Set<User> users) {
            if (users == null) return new ArrayList<>();
            return users.stream().map(User::get_id).collect(Collectors.toList());
        }
    };
}
