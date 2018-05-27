package com.vehicle.router.http;

import com.vehicle.router.model.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoutingRequestEntity {

    private int vehicle_capacity;
    private List<Node> nodes;

    public RoutingRequestEntity() {
        this.nodes = new ArrayList<>();
    }

    public RoutingRequestEntity(int vehicle_capacity, List<Node> nodes) {
        this.vehicle_capacity = vehicle_capacity;
        this.nodes = nodes;
    }

    public RoutingRequestEntity(int vehicle_capacity) {
        this(vehicle_capacity, new ArrayList<>());
    }

    public int getVehicle_capacity() {
        return vehicle_capacity;
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
        this.vehicle_capacity = vehicle_capacity;
    }

    @Override
    public String toString() {
        return String.format("[Max capacity: %d, nodes amount: %d]", vehicle_capacity, nodes.size());
    }
}
