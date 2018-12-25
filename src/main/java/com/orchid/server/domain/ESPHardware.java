package com.orchid.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "esp_hardware")
public class ESPHardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hardware_id")
    private String hardwareId;

    @Column(name = "dialogflowref")
    private String dialogFlowRef;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "last_value")
    private Integer lastValue;

    @Column(name = "last_logged")
    private Date lastLogged;

    @NotNull
    @ManyToOne
    @JoinColumn(name="espDevice")
    private ESPDevice espDevice;

    @OneToMany(mappedBy = "espHardware",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<ESPDeviceLog> espDeviceLogSet = new HashSet<ESPDeviceLog>();

    public ESPHardware() {

    }

    public ESPHardware(String hardwareId,String dialogFlowRef,String title,String type,Boolean enabled,Integer lastValue,Date lastLogged,ESPDevice espDevice) {
        this.hardwareId = hardwareId;
        this.dialogFlowRef = dialogFlowRef;
        this.title = title;
        this.type = type;
        this.enabled = enabled;
        this.lastValue = lastValue;
        this.lastLogged = lastLogged;
        this.espDevice = espDevice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getDialogFlowRef() {
        return dialogFlowRef;
    }

    public void setDialogFlowRef(String dialogFlowRef) {
        this.dialogFlowRef = dialogFlowRef;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getLastValue() {
        return lastValue;
    }

    public void setLastValue(Integer lastValue) {
        this.lastValue = lastValue;
    }

    public Date getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged(Date lastLogged) {
        this.lastLogged = lastLogged;
    }

    public ESPDevice getEspDevice() {
        return espDevice;
    }

    public void setEspDevice(ESPDevice espDevice) {
        this.espDevice = espDevice;
    }

    public Set<ESPDeviceLog> getEspDeviceLogSet() {
        return espDeviceLogSet;
    }

    public void setEspDeviceLogSet(Set<ESPDeviceLog> espDeviceLogSet) {
        this.espDeviceLogSet = espDeviceLogSet;
    }
}
