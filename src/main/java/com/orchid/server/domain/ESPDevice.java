package com.orchid.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "esp_device")
public class ESPDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "dialogflowref")
    private String dialogFlowRef;

    @NotNull
    @ManyToOne
    @JoinColumn(name="instance_id")
    private Instance instance;

    @JsonIgnore
    @Column(name = "device_secret")
    private String deviceSecret;

    @JsonIgnore
    @Column(name = "device_channel")
    private String deviceChannel;

    @Column(name = "local_ip_address")
    private String localIpAddress;

    @Column(name = "title")
    private String title;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(mappedBy = "espDevice",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<ESPHardware> espHardwares = new HashSet<ESPHardware>();

    public ESPDevice() {}

    public ESPDevice(String deviceId,String dialogFlowRef,Instance instance,String deviceSecret,String deviceChannel,String localIpAddress,String title,Boolean enabled) {
        this.deviceId = deviceId;
        this.dialogFlowRef = dialogFlowRef;
        this.instance = instance;
        this.deviceSecret = deviceSecret;
        this.deviceChannel = deviceChannel;
        this.localIpAddress = localIpAddress;
        this.title = title;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDialogFlowRef() {
        return dialogFlowRef;
    }

    public void setDialogFlowRef(String dialogFlowRef) {
        this.dialogFlowRef = dialogFlowRef;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public String getDeviceChannel() {
        return deviceChannel;
    }

    public void setDeviceChannel(String deviceChannel) {
        this.deviceChannel = deviceChannel;
    }

    public String getLocalIpAddress() {
        return localIpAddress;
    }

    public void setLocalIpAddress(String localIpAddress) {
        this.localIpAddress = localIpAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<ESPHardware> getEspHardwares() {
        return espHardwares;
    }

    public void setEspHardwares(Set<ESPHardware> espHardwares) {
        this.espHardwares = espHardwares;
    }
}
