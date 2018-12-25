package com.orchid.server.domain;

public class SocketMsg {

    private String message = null;
    private String command = null;

    public SocketMsg() {}

    public SocketMsg(String message, String command) {
        this.message = message;
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
