package com.llan.mahjongfunsies.ai.data;

import com.llan.mahjongfunsies.util.NumericMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSaver {
    private static final String location = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\llan\\mahjongfunsies\\weights\\";

    public static void save(NumericMatrix matrix, String filename){
        File file;
        FileWriter writer;
        try {
            file = new File(location + filename);
            file.createNewFile();
            writer = new FileWriter(file);
            writer.write(matrix.getDataString());
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static NumericMatrix retrieve(String filename){
        File file;
        Scanner scanner;
        try {
            file = new File(location + filename);
            scanner = new Scanner(file);
            List<String> scanned = new ArrayList<>();
            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                scanned.addLast(data);
            }
            scanner.close();
            return NumericMatrix.parseDataString(scanned);
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        return NumericMatrix.empty();
    }
}
