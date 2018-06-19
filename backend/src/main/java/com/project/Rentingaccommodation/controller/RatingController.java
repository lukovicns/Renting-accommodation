package com.project.Rentingaccommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.service.RatingService;

@RestController
@RequestMapping(value="/api/ratings")
public class RatingController {

    @Autowired
    private RatingService service;
}
