package com.feng.axon.config.command;

import model.User;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.annotation.MetaDataValue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface MyCommandGateway extends CommandGateway {
    /**
     * Sends the given {@code command}, and have the result of the command's execution reported to the given
     * {@code callback}.
     * <p/>
     * The given {@code command} is wrapped as the payload of the CommandMessage that is eventually posted on the
     * Command Bus, unless Command already implements {@link Message}. In that case, a
     * CommandMessage is constructed from that message's payload and MetaData.
     *
     * @param command   The command to dispatch
     * @param commander The user who sends this command
     * @param callback  The callback to notify when the command has been processed
     * @param <R>       The type of result expected from command execution
     */
    <C, R> void send(C command, @MetaDataValue("commander") User commander, CommandCallback<? super C, ? super R> callback);

    /**
     * Sends the given {@code command} and wait for it to execute. The result of the execution is returned when
     * available. This method will block indefinitely, until a result is available, or until the Thread is interrupted.
     * When the thread is interrupted, this method returns {@code null}. If command execution resulted in an
     * exception, it is wrapped in a {@link CommandExecutionException}.
     * <p/>
     * The given {@code command} is wrapped as the payload of the CommandMessage that is eventually posted on the
     * Command Bus, unless Command already implements {@link Message}. In that case, a
     * CommandMessage is constructed from that message's payload and MetaData.
     * <p/>
     * Note that the interrupted flag is set back on the thread if it has been interrupted while waiting.
     *
     * @param command   The command to dispatch
     * @param commander The user who sends this command
     * @param <R>       The type of result expected from command execution
     * @return the result of command execution, or {@code null} if the thread was interrupted while waiting for
     * the command to execute
     * @throws CommandExecutionException when an exception occurred while processing the command
     * @throws TimeoutException          when a timeout occurs
     * @throws InterruptedException      when the thread is interrupted while waiting for a result
     */
    <R> R sendAndWait(Object command, @MetaDataValue("commander") User commander) throws TimeoutException, InterruptedException;

    <R> R sendAndWait(Object command, MetaData metaData) throws TimeoutException, InterruptedException;

    /**
     * Sends the given {@code command} and wait for it to execute. The result of the execution is returned when
     * available. This method will block until a result is available, or the given {@code timeout} was reached, or
     * until the Thread is interrupted. When the timeout is reached or the thread is interrupted, this method returns
     * {@code null}. If command execution resulted in an exception, it is wrapped in a
     * {@link CommandExecutionException}.
     * <p/>
     * The given {@code command} is wrapped as the payload of the CommandMessage that is eventually posted on the
     * Command Bus, unless Command already implements {@link Message}. In that case, a
     * CommandMessage is constructed from that message's payload and MetaData.
     * <p/>
     * Note that the interrupted flag is set back on the thread if it has been interrupted while waiting.
     *
     * @param command   The command to dispatch
     * @param commander The user who sends this command
     * @param timeout   The amount of time the thread is allows to wait for the result
     * @param unit      The unit in which {@code timeout} is expressed
     * @param <R>       The type of result expected from command execution
     * @return the result of command execution, or {@code null} if the thread was interrupted while waiting for
     * the command to execute
     * @throws CommandExecutionException when an exception occurred while processing the command
     * @throws TimeoutException          when a timeout occurs
     * @throws InterruptedException      when the thread is interrupted while waiting for a result
     */
    <R> R sendAndWait(Object command, @MetaDataValue("commander") User commander, long timeout, TimeUnit unit)
            throws TimeoutException, InterruptedException;

    /**
     * Sends the given {@code command} and returns immediately, without waiting for the command to execute. The
     * caller will therefore not receive any feedback on the command's execution.
     * <p/>
     * The given {@code command} is wrapped as the payload of the CommandMessage that is eventually posted on the
     * Command Bus, unless Command already implements {@link Message}. In that case, a
     * CommandMessage is constructed from that message's payload and MetaData.
     *
     * @param command   The command to dispatch
     * @param commander The user who sends this command
     * @return a {@link CompletableFuture} which is resolved when the command is executed
     */
    <R> CompletableFuture<R> send(Object command, @MetaDataValue("commander") User commander);

    <R> CompletableFuture<R> send(Object command, MetaData metaData);
}
