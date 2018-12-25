package com.orchid.server.esp_device;

import com.orchid.server.domain.ESPDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ESPDeviceControllerREST {

    @Autowired
    ESPDeviceController espDeviceController;

    @RequestMapping(method = RequestMethod.GET, value="/getESPConfig/{deviceSecret}")
    public ResponseEntity<?> getESPConfig(@PathVariable("deviceSecret") String deviceSecret) {

        try {
            String config = espDeviceController.getESPConfig(deviceSecret);
            return new ResponseEntity<String>(config,HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/fetchESPDevicesByInstanceId/{instanceId}")
    public List<ESPDevice> fetchESPDevicesByInstanceId(@PathVariable("instanceId") Integer instanceId) {
        return espDeviceController.fetchESPDevicesByInstanceId(instanceId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/storeESPDevice")
    public ResponseEntity<?> storeESPDevice(@RequestBody ESPDevice espDevice) {
        try {
            ESPDevice newESPDevice = espDeviceController.storeESPDevice(espDevice);
            return new ResponseEntity<ESPDevice>(newESPDevice,HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/updateESPDevice")
    public ResponseEntity<?> updateESPDevice(@RequestBody ESPDevice espDevice) {
        try {
            ESPDevice updatedESPDevice = espDeviceController.updateESPDevice(espDevice);
            return new ResponseEntity<ESPDevice>(updatedESPDevice,HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/deleteESPDevice")
    public ResponseEntity<?> deleteESPDevice(@RequestBody ESPDevice espDevice) {
        try {
            espDeviceController.deleteESPDevice(espDevice);
            return new ResponseEntity<String>("ESPDevice deleted",HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<String>("Somethings wrong.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
