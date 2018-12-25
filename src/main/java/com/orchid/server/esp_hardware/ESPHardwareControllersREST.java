package com.orchid.server.esp_hardware;

import com.orchid.server.domain.ESPHardware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class ESPHardwareControllersREST {

    @Autowired
    ESPHardwareController espHardwareController;

    @RequestMapping(method = RequestMethod.GET, value="/fetchAllESPHardwaresByDeviceId/{deviceID}")
    public ResponseEntity<?> fetchAllESPHardwaresByDeviceId(@PathVariable("deviceID") Integer deviceID) {

        Map<String,List<ESPHardware>> summary = espHardwareController.fetchAllESPHardwaresByDeviceId(deviceID);
        if (summary == null) {
            return new ResponseEntity<Object>("Device not registered.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(summary,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value="/addESPHardware")
    public ResponseEntity<?> addESPHardware(@RequestBody ESPHardware hardware) {
        try {
            return new ResponseEntity<Object>(espHardwareController.addESPHardware(hardware),HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/updateESPHardware")
    public ResponseEntity<?> updateESPHardware(@RequestBody ESPHardware hardware) {
        try {
            return new ResponseEntity<Object>(espHardwareController.updateESPHardware(hardware),HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/deleteESPHardware")
    public void deleteESPHardware(@RequestBody ESPHardware hardware) {
        espHardwareController.deleteESPHardware(hardware);
    }
}
