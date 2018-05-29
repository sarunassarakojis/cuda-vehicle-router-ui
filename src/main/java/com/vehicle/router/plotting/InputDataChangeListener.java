package com.vehicle.router.plotting;

import com.vehicle.router.model.Node;
import javafx.collections.ListChangeListener;

public class InputDataChangeListener implements ListChangeListener<Node> {

    private final GraphPlotter graphPlotter;

    public InputDataChangeListener(GraphPlotter graphPlotter) {
        this.graphPlotter = graphPlotter;
    }

    @Override
    public void onChanged(Change<? extends Node> change) {
        while (change.next()) {
            boolean wasReplaced = change.wasReplaced();

            graphPlotter.invalidateRoutes();

            if (change.wasAdded() && !wasReplaced) {
                graphPlotter.addNode(change.getFrom());
            } else if (wasReplaced) {
                graphPlotter.updateNode(change.getFrom());
            } else if (change.wasRemoved()) {
                graphPlotter.deleteNode(change.getFrom());
            }
        }
    }
}
