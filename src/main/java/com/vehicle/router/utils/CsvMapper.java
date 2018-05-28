package com.vehicle.router.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {

    private static final String INDICE = "indice";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String DEMAND = "demand";

    public static List<Node> readFile(String path) {
        List<Node> nodes = new ArrayList<>();
        try (Reader reader = new FileReader(path)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(reader);
            for (CSVRecord record : records) {
                nodes.add(mapToNode(record.get(INDICE), record.get(X), record.get(Y), record.get(DEMAND)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodes;
    }

    private static Node mapToNode(String indiceString, String xString, String yString, String demandString) {
        int indice = Integer.parseInt(indiceString);
        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int demand = Integer.parseInt(demandString);
        return new Node(indice, x, y, demand);
    }

    public static class Node {
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

        public void setIndice(int indice) {
            this.indice = indice;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getDemand() {
            return demand;
        }

        public void setDemand(int demand) {
            this.demand = demand;
        }
    }
}
