package com.vehicle.router.utils;

import com.vehicle.router.model.Node;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private static final String INDICE = "indice";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String DEMAND = "demand";

    public static List<Node> readCsvExcelFile(File file) throws IOException {
        List<Node> nodes = new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(reader);

            for (CSVRecord record : records) {
                nodes.add(mapToNode(record.get(INDICE), record.get(X), record.get(Y), record.get(DEMAND)));
            }
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
}
