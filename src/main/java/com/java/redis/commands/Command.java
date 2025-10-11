package com.java.redis.commands;

import com.java.redis.models.ClientRequest;

import java.io.OutputStream;
import java.util.List;


public abstract class Command {

    public abstract void validateCommand(List<String> args) throws Exception;

    public abstract void executeCommand(OutputStream outputStream, ClientRequest clientRequest) throws Exception;
}
