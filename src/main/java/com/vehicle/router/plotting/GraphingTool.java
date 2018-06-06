package com.vehicle.router.plotting;

import com.vehicle.router.model.Node;
import com.vehicle.router.model.Route;
import org.graphstream.graph.Graph;

import java.util.List;

public class GraphingTool {

    private final Graph graph;

    public GraphingTool(Graph graph) {
        this.graph = graph;
    }

    public void addDepot(int x, int y) {
        // TODO: 6/5/2018 Extract common attributes/IDs to final class
        org.graphstream.graph.Node depot = graph.addNode("D");

        depot.setAttribute("xy", x, y);
        depot.setAttribute("ui.label", "Depot");
        depot.setAttribute("ui.class", "depot");
    }

    public void addNode(Node node) {
        org.graphstream.graph.Node graphNode = graph.addNode(String.valueOf(node.getIndice()));

        graphNode.setAttribute("xy", node.getX(), node.getY());
        graphNode.setAttribute("ui.label", node.getIndice());
    }

    public void addNodes(List<? extends Node> nodes) {
        for (Node node : nodes) {
            addNode(node);
        }
    }

    public void updateNode(Node node) {
        org.graphstream.graph.Node graphNode = graph.getNode(String.valueOf(node.getIndice()));

        if (graphNode != null) {
            graphNode.setAttribute("xy", node.getX(), node.getY());
        }
    }

    public void removeNode(Node node) {
        graph.removeNode(String.valueOf(node.getIndice()));
    }

    public void removeNodes(List<? extends Node> nodes) {
        for (Node node : nodes) {
            removeNode(node);
        }
    }

    public void addRoute(Route route) {
        int[] nodes = route.getNodes();

        if (nodes.length > 0) {
            graph.addEdge("D-" + nodes[0], "D", String.valueOf(nodes[0]));

            for (int i = 1; i < nodes.length; ++i) {
                graph.addEdge("" + nodes[i - 1] + "-" + nodes[i], String.valueOf(nodes[i - 1]), String.valueOf(nodes[i]));
            }

            graph.addEdge(nodes[nodes.length - 1] + "-D", String.valueOf(nodes[nodes.length - 1]), "D");
        }
    }

    public void addRoutes(List<? extends Route> routes) {
        for (Route route : routes) {
            addRoute(route);
        }
    }

    public void clearRoutes() {
        for (int i = graph.getEdgeCount() - 1; i >= 0; --i) {
            graph.removeEdge(i);
        }
    }
}
