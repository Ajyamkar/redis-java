package com.java.redis.commands;

import com.java.redis.models.ClientRequest;

import java.io.OutputStream;

public abstract class Command {
    public OutputStream outputStream;
    public ClientRequest clientRequest;

    public void executeCommand() {
    }

    ;
}
