package com.vehicle.router.http.consumer;

import com.vehicle.router.http.RoutingApiTargets;
import com.vehicle.router.model.DeviceInfo;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class DeviceInfoServiceConsumer {

    public static List<DeviceInfo> consumeDeviceInfoService() {
        return RoutingApiTargets.getDevicesTarget()
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<DeviceInfo>>() {});
    }
}