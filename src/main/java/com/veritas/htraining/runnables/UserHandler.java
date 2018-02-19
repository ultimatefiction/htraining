package com.veritas.htraining.runnables;

import com.google.protobuf.InvalidProtocolBufferException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.veritas.htraining.model.User;

import java.io.UnsupportedEncodingException;

public class UserHandler extends DefaultConsumer {

    public UserHandler(Channel channel) {
        super(channel);
    }

    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
        try {
            User user = new User(body);
            System.out.printf("[<] %s%n", user.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
