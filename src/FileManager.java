import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private static final String DATA_FILE = "datas/";

    public static String[] readDataFromFile(String path) {
        String[] datas = null;
        try{
            File data = new File(DATA_FILE + path);
            if (data.exists()) {
                Scanner scanner = new Scanner(data);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    datas = line.split(" , ");
                }
            }
        } catch (IOException e){
            System.out.println("File not found!");
        }

        return datas;
    }

    public static void writeDataToFile(String data, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE + path, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }
}
