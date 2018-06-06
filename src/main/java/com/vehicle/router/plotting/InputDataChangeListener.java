package com.vehicle.router.plotting;

import com.vehicle.router.model.Node;
import javafx.collections.ListChangeListener;

import java.util.List;

public class InputDataChangeListener implements ListChangeListener<Node> {

    private final GraphingTool graphingTool;

    public InputDataChangeListener(GraphingTool graphingTool) {
        this.graphingTool = graphingTool;
    }

    @Override
    public void onChanged(Change<? extends Node> change) {
        while (change.next()) {
            boolean wasReplaced = change.wasReplaced();

            if (change.wasAdded() && !wasReplaced) {
                addNodesOnTask(change.getAddedSubList());
            } else if (wasReplaced) {
                graphingTool.updateNode(change.getList().get(change.getFrom()));
            } else if (change.wasRemoved()) {
                removeNodesOnTask(change.getRemoved());
            }
        }
    }

    private void addNodesOnTask(List<? extends Node> nodes) {
        new Thread(new DelegateTask<>(() -> {
            graphingTool.addNodes(nodes);
            return true;
        })).start();
    }

    private void removeNodesOnTask(List<? extends Node> nodes) {
        new Thread(new DelegateTask<>(() -> {
            graphingTool.removeNodes(nodes);
            return true;
        })).start();
    }
}
