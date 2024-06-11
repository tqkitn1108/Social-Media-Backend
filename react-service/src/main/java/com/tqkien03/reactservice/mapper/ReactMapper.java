package com.tqkien03.reactservice.mapper;

import com.tqkien03.reactservice.client.UserFeignClient;
import com.tqkien03.reactservice.dto.ReactDto;
import com.tqkien03.reactservice.dto.UserSummary;
import com.tqkien03.reactservice.exception.ResourceNotFoundException;
import com.tqkien03.reactservice.model.React;
import com.tqkien03.reactservice.model.ReactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReactMapper {
    private final UserFeignClient userFeignClient;

    public ReactDto toReactDto(React react, String myId) {
        return ReactDto.builder()
                .id(react.getId())
                .type(react.getType().toString())
                .postId(react.getPostId())
                .user(getUserSummary(react, myId))
                .createdAt(react.getCreatedAt())
                .build();
    }

    public List<ReactDto> reactsToReactDtos(List<React> reacts, String myId) {
        return reacts.stream()
                .map(react -> toReactDto(react, myId))
                .collect(Collectors.toList());
    }

    public React reactDtoToReact(ReactDto reactDto) {
        return React.builder()
                .id(reactDto.getId())
                .type(ReactionType.valueOf(reactDto.getType()))
                .postId(reactDto.getPostId())
                .userId(reactDto.getUser().getId())
                .createdAt(reactDto.getCreatedAt())
                .build();
    }

    UserSummary getUserSummary(React react, String myId) {
        return userFeignClient.getUserSummary(react.getUserId(), myId)
                .orElseThrow(() -> new ResourceNotFoundException(react.getUserId()));
    }
}
