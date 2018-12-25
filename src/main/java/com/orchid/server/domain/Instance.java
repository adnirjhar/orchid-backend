package com.orchid.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instance")
public class Instance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "dialogflowref")
    private String dialogFlowRef;

    @Column(name = "title")
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name="created_by")
    private User createdBy;

    @OneToMany(mappedBy = "instance",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<ESPDevice> espDevice = new HashSet<ESPDevice>();

    public Instance() {}

    public Instance(String name, String dialogFlowRef, String title, User creator) {
        this.name = name;
        this.dialogFlowRef = dialogFlowRef;
        this.title = title;
        this.createdBy = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<ESPDevice> getEspDevice() {
        return espDevice;
    }

    public void setEspDevice(Set<ESPDevice> espDevice) {
        this.espDevice = espDevice;
    }
}
