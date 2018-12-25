package com.orchid.server.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardREST {

    @Autowired
    DashboardService dashboardService;

    @RequestMapping(method = RequestMethod.GET, value="/widgetSummary")
    public ResponseEntity<?> widgetSummary() {
        return new ResponseEntity<Object>(dashboardService.widgetSummary(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value="/logSummary")
    public ResponseEntity<?> getSummary() {
        return new ResponseEntity<Object>(dashboardService.deviceLogSummary(),HttpStatus.OK);
    }
}
