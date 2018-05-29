package com.vehicle.router.plotting;

import com.vehicle.router.model.Route;
import javafx.collections.ListChangeListener;

public class RoutesSolveListener implements ListChangeListener<Route> {

    private final GraphPlotter graphPlotter;

    public RoutesSolveListener(GraphPlotter graphPlotter) {
        this.graphPlotter = graphPlotter;
    }

    @Override
    public void onChanged(Change<? extends Route> change) {
        while (change.next()) {
            if (change.wasAdded()){
                graphPlotter.plotRoutes(change.getList());
            }
        }
    }
}
