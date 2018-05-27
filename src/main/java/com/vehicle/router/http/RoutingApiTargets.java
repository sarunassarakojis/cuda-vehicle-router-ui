package com.vehicle.router.http;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public final class RoutingApiTargets {

    private static final String ROUTING = "routing";
    private static final String SEQUENTIAL = "sequential";
    private static final String PARALLEL = "parallel";
    private static final String DEVICES = "devices";

    public static WebTarget getSequentialRoutingTarget() {
        return getRoutingTarget(SEQUENTIAL);
    }

    public static WebTarget getParallelRoutingTarget() {
        return getRoutingTarget(PARALLEL);
    }

    public static WebTarget getDevicesTarget() {
        return ClientBuilder.newClient().target("http://localhost:6060")
                .path(DEVICES);
    }

    private static WebTarget getRoutingTarget(String type) {
        return ClientBuilder.newClient().target("http://localhost:6060")
                .path(ROUTING).path(type);
    }
}
