package org.guzzing.studayserver.domain.like.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studayserver.domain.like.service.LikeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/likes")
public class LikeRestController {

    private final LikeService likeService;

    public LikeRestController(LikeService likeService) {
        this.likeService = likeService;
    }

}
