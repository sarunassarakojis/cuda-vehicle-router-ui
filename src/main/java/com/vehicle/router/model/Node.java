package com.vehicle.router.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Node {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty x;
    private final SimpleIntegerProperty y;
    private final SimpleIntegerProperty demand;

    public Node(int id, int x, int y, int demand) {
        this.id = new SimpleIntegerProperty(id);
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.demand = new SimpleIntegerProperty(demand);
    }

    public int getId() {
        return id.get();
    }

    public int getX() {
        return x.get();
    }

    public int getY() {
        return y.get();
    }

    public int getDemand() {
        return demand.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setX(int x) {
        this.x.set(x);
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public void setDemand(int demand) {
        this.demand.set(demand);
    }
}
