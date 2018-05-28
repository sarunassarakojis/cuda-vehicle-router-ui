package com.vehicle.router.http;

import com.vehicle.router.model.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoutingRequestEntity {

    private int vehicleCapacity;
    private List<Node> nodes;

    public RoutingRequestEntity() {
        this.nodes = new ArrayList<>();
    }

    public RoutingRequestEntity(int vehicleCapacity, List<Node> nodes) {
        this.vehicleCapacity = vehicleCapacity;
        this.nodes = nodes;
    }

    public RoutingRequestEntity(int vehicleCapacity) {
        this(vehicleCapacity, new ArrayList<>());
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addNodes(Collection<Node> nodes) {
        this.nodes.addAll(nodes);
    }

    public void setVehicleCapacity(int vehicle_capacity) {
        this.vehicleCapacity = vehicle_capacity;
    }

    @Override
    public String toString() {
        return String.format("[Max capacity: %d, nodes amount: %d]", vehicleCapacity, nodes.size());
    }
}
