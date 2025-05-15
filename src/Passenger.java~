import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_FILE = "datas/";

    public FileManager() {
        // Make sure the data directory exists
        File dataDir = new File(DATA_FILE);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
            System.out.println("Created data directory on initialization: " + dataDir.getAbsolutePath());
        }
    }

    public String[] readDataFromFile(String path) {
        String[] datas = new String[100];
        int i = 0;
        try {
            File data = new File(DATA_FILE + path);
            System.out.println("Reading from file: " + data.getAbsolutePath());

            if (data.exists()) {
                Scanner scanner = new Scanner(data);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    datas[i] = line;
                    i++;
                }
                scanner.close();
                System.out.println("Successfully read " + i + " lines from file");
            } else {
                System.out.println("File does not exist: " + data.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return datas;
    }

    public void writeDataToFile(String data, String path) {
        try {
            File file = new File(DATA_FILE + path);

            // Create parent directory if it doesn't exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // If file doesn't exist, create it
            if (!file.exists()) {
                file.createNewFile();
            }

            System.out.println("Writing to file: " + file.getAbsolutePath());
            System.out.println("Data to write: " + data);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(data);
            writer.close();

            System.out.println("Successfully wrote data to file");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Check if routes overlap
    public boolean routesOverlap(String route1Start, String route1End,
                                 String route2Start, String route2End) {
        // Get indices for cities
        int r1s = getCityIndex(route1Start);
        int r1e = getCityIndex(route1End);
        int r2s = getCityIndex(route2Start);
        int r2e = getCityIndex(route2End);

        // Ensure start index is smaller than end index
        if (r1s > r1e) {
            int temp = r1s;
            r1s = r1e;
            r1e = temp;
        }

        if (r2s > r2e) {
            int temp = r2s;
            r2s = r2e;
            r2e = temp;
        }

        // Check for overlap
        // Two routes overlap if one route's start is between another route's start and end
        // OR if one route's end is between another route's start and end
        return (r2s >= r1s && r2s < r1e) || // route2's start is within route1
                (r2e > r1s && r2e <= r1e) || // route2's end is within route1
                (r1s >= r2s && r1s < r2e) || // route1's start is within route2
                (r1e > r2s && r1e <= r2e);   // route1's end is within route2
    }

    private int getCityIndex(String city) {
        switch (city) {
            case "MAGELANG": return 0;
            case "JOGJA": return 1;
            case "KARTOSURO": return 2;
            case "SOLO": return 3;
            case "GENDINGAN": return 4;
            case "NGAWI": return 5;
            case "WILANGAN": return 6;
            default: return -1;
        }
    }

    // Get all bookings for a specific seat
    public List<Booking> getBookingsForSeat(String seatNumber) {
        List<Booking> bookings = new ArrayList<>();
        String[] bookingData = readDataFromFile("data_penumpang.txt");

        for (String data : bookingData) {
            if (data == null || data.trim().isEmpty()) continue;

            String[] parts = data.split(" , ");
            if (parts.length >= 6 && parts[0].trim().equals(seatNumber)) {
                Booking booking = new Booking(
                        parts[0].trim(),    // seat
                        parts[1].trim(),    // name
                        parts[2].trim(),    // nik
                        parts[3].trim(),    // phone
                        parts[4].trim(),    // departure
                        parts[5].trim()     // arrival
                );
                bookings.add(booking);
            }
        }

        return bookings;
    }

    // Helper class to store booking information
    public static class Booking {
        String seat;
        String name;
        String nik;
        String phone;
        String departure;
        String arrival;

        public Booking(String seat, String name, String nik, String phone,
                       String departure, String arrival) {
            this.seat = seat;
            this.name = name;
            this.nik = nik;
            this.phone = phone;
            this.departure = departure;
            this.arrival = arrival;
        }
    }

    // Static versions for backward compatibility
    public static String[] readDataFromFile_static(String path) {
        return new FileManager().readDataFromFile(path);
    }

    public static void writeDataToFile_static(String data, String path) {
        new FileManager().writeDataToFile(data, path);
    }
}