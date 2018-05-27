package com.vehicle.router.model;

public class Node {

    private int indice;
    private int x;
    private int y;
    private int demand;

    public Node(int indice, int x, int y, int demand) {
        this.indice = indice;
        this.x = x;
        this.y = y;
        this.demand = demand;
    }

    public int getIndice() {
        return indice;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDemand() {
        return demand;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    @Override
    public String toString() {
        return String.format("[indice: %d, x: %d, y: %d, demand: %d]", indice, x, y, demand);
    }
}
