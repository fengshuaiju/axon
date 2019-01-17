package com.feng.axon.config;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.annotation.MetaDataValue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface MyCommandGateway {
    <C, R> void send(C var1, CommandCallback<? super C, ? super R> var2);

    <R> R sendAndWait(Object var1);

    <R> R sendAndWait(Object var1, long var2, TimeUnit var4);

    <R> CompletableFuture<R> send(Object var1);

    <R> CompletableFuture<R> send(Object var1, MetaData metaData);

    <R> CompletableFuture<R> send(Object var1, @MetaDataValue("metaData") Person metaData);
}
