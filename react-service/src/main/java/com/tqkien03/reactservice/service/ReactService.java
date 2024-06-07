package com.tqkien03.reactservice.service;

import com.tqkien03.reactservice.client.PostFeignClient;
import com.tqkien03.reactservice.dto.ReactDto;
import com.tqkien03.reactservice.dto.ReactRequest;
import com.tqkien03.reactservice.exception.NotAllowedException;
import com.tqkien03.reactservice.exception.ResourceNotFoundException;
import com.tqkien03.reactservice.mapper.ReactMapper;
import com.tqkien03.reactservice.messaging.ReactProducer;
import com.tqkien03.reactservice.model.React;
import com.tqkien03.reactservice.model.ReactionType;
import com.tqkien03.reactservice.repository.ReactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactService {
    private final ReactRepository reactRepository;
    private final ReactMapper reactMapper;
    private final PostFeignClient postFeignClient;
    private final ReactProducer reactProducer;

    public boolean addReact(ReactRequest request, Authentication authentication) {
        try {
            int postId = request.getPostId();
            postFeignClient.getPost(postId, authentication)
                    .orElseThrow(() -> new ResourceNotFoundException(postId + "not found"));
            String userId = authentication.getName();
            if (!request.getUserId().equals(userId)) {
                throw new NotAllowedException(userId, String.valueOf(postId), "comment");
            }

            Optional<React> reactOptional = reactRepository.findByPostIdAndUserId(postId, userId);
            if (reactOptional.isPresent()) {
                reactRepository.delete(reactOptional.get());
            } else {
                React react = React
                        .builder()
                        .type(ReactionType.valueOf(request.getType()))
                        .postId(postId)
                        .userId(userId)
                        .build();
                reactRepository.save(react);
                reactProducer.sendReactCreated(react);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ReactDto> getReacts(Integer postId, Authentication authentication, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<React> reacts = reactRepository.findByPostId(postId, pageable);
        return reactMapper.reactsToReactDtos(reacts, authentication);
    }
}
