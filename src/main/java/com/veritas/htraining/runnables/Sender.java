package com.veritas.htraining.runnables;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.veritas.htraining.model.Permission;
import com.veritas.htraining.model.Status;
import com.veritas.htraining.model.User;
import com.veritas.htraining.utils.ConnectionProvider;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

public class Sender extends Thread {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        User user = new User();
        user.setId(1);
        user.setLogin("Foo");
        user.setPassword("Bar");
        user.setEmail("foo@bar.com");
        user.setStatus(Status.ACTIVE);

        LinkedList<Permission> list = new LinkedList<>();
        list.add(new Permission("kek", 1));
        list.add(new Permission("mda", 2));
        user.setPermissions(list);

        Channel channel = new ConnectionProvider().getChannel();
        final String EXCHANGE_NAME = "user_exchange";

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        Consumer consumer = new UserHandler(channel);
        channel.basicConsume(queueName, true, consumer);

        Thread.sleep(1000);

        //channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.basicPublish(EXCHANGE_NAME, "", null, user.getBytes());
        System.out.printf("[>] %s%n", user.toString());

    }

}
