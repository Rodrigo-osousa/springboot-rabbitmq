package com.springbootrabbitmq.controller;

import com.springbootrabbitmq.service.RabbitmqService;
import constants.RabbitmqConstants;
import dto.IventaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/iventary")
public class IventaryController {

    private static final Logger LOGGER = Logger.getLogger(IventaryController.class.getName());

    @Autowired
    private RabbitmqService rabbitmqService;

    @PutMapping
    private ResponseEntity changeIventary(@RequestBody IventaryDto iventaryDto) {
        LOGGER.info("New Iventary message created");
        this.rabbitmqService.sendMessage(RabbitmqConstants.QUEUE_INVENTORY, iventaryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
