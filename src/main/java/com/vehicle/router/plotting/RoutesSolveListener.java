package com.vehicle.router.plotting;

import com.vehicle.router.model.Route;
import javafx.collections.ListChangeListener;

import java.util.List;

public class RoutesSolveListener implements ListChangeListener<Route> {

    private final GraphingTool graphingTool;

    public RoutesSolveListener(GraphingTool graphingTool) {
        this.graphingTool = graphingTool;
    }

    @Override
    public void onChanged(Change<? extends Route> change) {
        while (change.next()) {
            if (change.wasAdded()){
                addRoutesOnTask(change.getList());
            }
        }
    }

    private void addRoutesOnTask(List<? extends Route> routes) {
        new Thread(new DelegateTask<>(() -> {
            graphingTool.clearRoutes();
            graphingTool.addRoutes(routes);
            return true;
        })).start();
    }
}
