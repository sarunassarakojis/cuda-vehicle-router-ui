package com.vehicle.router.model;

public class DeviceInfo {

    private String computeCapability;
    private String deviceName;
    private int driverVersion;
    private int globalMemoryMegabytes;
    private int maxThreadsPerBlock;

    public DeviceInfo() {
    }

    public DeviceInfo(String computeCapability, String deviceName, int driverVersion, int globalMemoryMegabytes, int maxThreadsPerBlock) {
        this.computeCapability = computeCapability;
        this.deviceName = deviceName;
        this.driverVersion = driverVersion;
        this.globalMemoryMegabytes = globalMemoryMegabytes;
        this.maxThreadsPerBlock = maxThreadsPerBlock;
    }

    public String getComputeCapability() {
        return computeCapability;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getDriverVersion() {
        return driverVersion;
    }

    public int getGlobalMemoryMegabytes() {
        return globalMemoryMegabytes;
    }

    public int getMaxThreadsPerBlock() {
        return maxThreadsPerBlock;
    }
}
