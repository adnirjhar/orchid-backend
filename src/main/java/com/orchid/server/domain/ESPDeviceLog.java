package com.orchid.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "esp_device_log")
public class ESPDeviceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    private String data;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "logged_at")
    private Date loggedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name="espDevice")
    @JsonIgnore
    private ESPDevice espDevice;

    @NotNull
    @ManyToOne
    @JoinColumn(name="esp_hardware")
    @JsonIgnore
    private ESPHardware espHardware;

    public ESPDeviceLog() {

    }

    public ESPDeviceLog(String data,String dataType,Date loggedAt,ESPDevice espDevice,ESPHardware espHardware) {
        this.data = data;
        this.dataType = dataType;
        this.loggedAt = loggedAt;
        this.espDevice = espDevice;
        this.espHardware = espHardware;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Date getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(Date loggedAt) {
        this.loggedAt = loggedAt;
    }

    public ESPDevice getEspDevice() {
        return espDevice;
    }

    public void setEspDevice(ESPDevice espDevice) {
        this.espDevice = espDevice;
    }

    public ESPHardware getEspHardware() {
        return espHardware;
    }

    public void setEspHardware(ESPHardware espHardware) {
        this.espHardware = espHardware;
    }
}
