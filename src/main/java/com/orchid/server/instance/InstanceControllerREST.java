package com.orchid.server.instance;

import com.orchid.server.domain.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class InstanceControllerREST {

    @Autowired
    InstanceController instanceController;

    @RequestMapping(method = RequestMethod.GET, value="/fetchAllInstances")
    public ResponseEntity<?> fetchAllInstances() {
        return new ResponseEntity<List<Instance>>(instanceController.fetchAllInstances(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value="/updateInstance")
    public ResponseEntity<?> updateInstance(@RequestBody Instance instance) {
        try {
            return new ResponseEntity<Instance>(instanceController.updateInstance(instance),HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/deleteInstance")
    public ResponseEntity<?> deleteInstance(@RequestBody Instance instance) {
        instanceController.deleteInstance(instance);
        return new ResponseEntity<Object>("Done",HttpStatus.OK);
    }
}
