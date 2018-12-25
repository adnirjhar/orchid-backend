package com.orchid.server.dialogflow;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2beta1.model.GoogleCloudDialogflowV2EventInput;
import com.google.api.services.dialogflow.v2beta1.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2beta1.model.GoogleCloudDialogflowV2WebhookResponse;
import com.google.gson.Gson;
import com.orchid.server.account.UserController;
import com.orchid.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DialogFlowREST {

    @Autowired
    UserController userController;

    @Autowired
    DialogFlowController dialogFlowController;

    private static JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

    @RequestMapping(method = RequestMethod.POST, value = "/dialogFlowWebHook")
    public ResponseEntity<?> dialogFlowWebHook(@RequestBody String requestStr,HttpServletRequest servletRequest) throws IOException {

        try {
            String email = servletRequest.getHeader("username");
            String password = servletRequest.getHeader("password");
            this.userController.authenticate(new User(email,password,null,null));

            GoogleCloudDialogflowV2WebhookResponse response = new GoogleCloudDialogflowV2WebhookResponse();
            GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(requestStr).parse(GoogleCloudDialogflowV2WebhookRequest.class);

            Map<String,Object> params = request.getQueryResult().getParameters();
            if (params.size() > 0) {
                response.setFulfillmentText(dialogFlowController.processMessage(params));
            }
            else {
                response.setFulfillmentText("Sorry you didn't send enough to process");
            }

            return new ResponseEntity<GoogleCloudDialogflowV2WebhookResponse>(response,HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
