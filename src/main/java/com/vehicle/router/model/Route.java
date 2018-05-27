package com.vehicle.router.model;

public class Route {

    private int metDemand;
    private int[] nodes;

    public Route() {
    }

    public int getMetDemand() {
        return metDemand;
    }

    public int[] getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return String.format("[Met demand: %d, nodes: %s]", metDemand, getNodesAsString());
    }

    public String getNodesAsString() {
        StringBuilder builder = new StringBuilder(this.nodes.length);

        for (int i = 0; i < nodes.length - 1; ++i) {
            builder.append(nodes[i]).append(" - ");
        }
        builder.append(nodes[nodes.length - 1]);

        return builder.toString();
    }
}
