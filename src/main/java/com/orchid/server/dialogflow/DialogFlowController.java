package com.orchid.server.dialogflow;


import java.util.Map;

public interface DialogFlowController {

    public String processMessage(Map<String, Object> parameters);
}
