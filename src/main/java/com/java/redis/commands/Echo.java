package com.java.redis.commands;

import com.java.redis.models.ClientRequest;
import com.java.redis.utils.SuccessResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Echo extends Command {

    public final SuccessResponseConstructor successResponseConstructor;

    public Echo(OutputStream outputStream, ClientRequest clientRequest){
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        successResponseConstructor = new SuccessResponseConstructor();
    }

    @Override
    public void executeCommand()  {
        try {
            outputStream.write(successResponseConstructor.construct(this.clientRequest.getArgs().getFirst()));
            outputStream.flush();
        } catch (IOException io) {
            throw new RuntimeException("I/O error while replying to client", io);
        }
    }
}
