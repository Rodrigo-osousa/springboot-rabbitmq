package com.springbootrabbitmq.controller;


import com.springbootrabbitmq.service.RabbitmqService;
import constants.RabbitmqConstants;
import dto.PriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/price")
public class PriceController {

    private static final Logger LOGGER = Logger.getLogger(PriceController.class.getName());

    @Autowired
    private RabbitmqService rabbitmqService;

    @PutMapping
    private ResponseEntity changePrice(@RequestBody PriceDto priceDto) {
        LOGGER.info("New Price message created");
        this.rabbitmqService.sendMessage(RabbitmqConstants.QUEUE_PRICE, priceDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
