package com.vehicle.router.http.consumer;

import com.vehicle.router.http.RoutingApiTargets;
import com.vehicle.router.http.RoutingRequestEntity;
import com.vehicle.router.model.Route;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class RoutingServiceConsumer {

    public static List<Route> consumeParallelRoutingService(RoutingRequestEntity routingEntity) {
        return consumeRoutingService(routingEntity, RoutingApiTargets.getParallelRoutingTarget());
    }

    public static List<Route> consumeSequentialRoutingService(RoutingRequestEntity routingEntity) {
        return consumeRoutingService(routingEntity, RoutingApiTargets.getSequentialRoutingTarget());
    }

    private static List<Route> consumeRoutingService(RoutingRequestEntity routingEntity, WebTarget routingTarget) {
        return routingTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(routingEntity, MediaType.APPLICATION_JSON), new GenericType<List<Route>>() {});
    }

}
