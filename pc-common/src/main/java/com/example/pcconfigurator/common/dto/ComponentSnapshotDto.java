package com.example.pcconfigurator.common.dto;

public class ComponentSnapshotDto {

    private String typeName;

    private String socket;
    private String ramType;
    private String formFactor;

    private Integer gpuLength;
    private Integer psuPower;
    private Integer tdp;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getRamType() {
        return ramType;
    }

    public void setRamType(String ramType) {
        this.ramType = ramType;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public Integer getGpuLength() {
        return gpuLength;
    }

    public void setGpuLength(Integer gpuLength) {
        this.gpuLength = gpuLength;
    }

    public Integer getPsuPower() {
        return psuPower;
    }

    public void setPsuPower(Integer psuPower) {
        this.psuPower = psuPower;
    }

    public Integer getTdp() {
        return tdp;
    }

    public void setTdp(Integer tdp) {
        this.tdp = tdp;
    }
}
