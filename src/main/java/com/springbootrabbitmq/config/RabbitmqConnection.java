package com.springbootrabbitmq.config;

import com.springbootrabbitmq.constants.RabbitmqConstants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class RabbitmqConnection {

    private static final String NOME_EXCHANGE = "AMQ.DIRECT";

    private AmqpAdmin amqpAdmin;

    public RabbitmqConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String nameQueue) {
        return new Queue(nameQueue,true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relationship(Queue queue, DirectExchange directExchange) {
        return new  Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange().getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add() {
        Queue queueIventory = this.queue(RabbitmqConstants.QUEUE_INVENTORY);
        Queue queuePrice = this.queue(RabbitmqConstants.QUEUE_PRICE);

        DirectExchange exchange = this.directExchange();

        Binding linkIventory = this.relationship(queueIventory, exchange);
        Binding linkPrice = this.relationship(queuePrice, exchange);

        this.amqpAdmin.declareQueue(queueIventory);
        this.amqpAdmin.declareQueue(queuePrice);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(linkIventory);
        this.amqpAdmin.declareBinding(linkPrice);
    }

}
