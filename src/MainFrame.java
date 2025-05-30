import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.io.File;

public class MainFrame extends JFrame {
    final Image ICON = new ImageIcon("assets/icon.png").getImage();
    final Image BACKGROUND = new ImageIcon("assets/bg2.jpg").getImage();
    final int SCREEN_WIDTH = 800;
    final int SCREEN_HEIGHT = 800;

    private String departure, arrival;
    private final List<JButton> allSeats = new ArrayList<>();
    private final Set<String> selectedSeats = new LinkedHashSet<>();
    private final Map<String, JButton> seatButtonMap = new HashMap<>();
    private JPanel passengerPanel;
    private JScrollPane passengerScrollPane;
    private BackgroundPanel mainPanel; // Changed to BackgroundPanel
    private JButton backButton, nextButton, resetButton, kembaliButton, historyButton;
    private boolean isFillingForm = false;
    private JLabel pricept;
    private double price;
    private Passenger passenger = new Passenger();
    private final Color darkBlue = new Color(0x27548A);
    private final Color seatPanelColor = new Color(0x61A2CB);
    private final Color beige = new Color(0xECDFBA);
    private final Color seatReservedColor = new Color(0x808080); // Gray for reserved
    private final Color seatOverlapColor = new Color(0xFF6B6B);  // Red for overlapping routes

    // Custom JPanel with background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw the background image to fill the panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public void setPrice(int price) {
        this.price = price;
        updatePriceLabel();
    }

    public MainFrame() {
        setTitle("Pilih Kursi Bus");
        setIconImage(ICON);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create background panel instead of plain JPanel
        mainPanel = new BackgroundPanel(BACKGROUND);
        setContentPane(mainPanel);

        JLabel labelKota = new JLabel();
        labelKota.setText(departure + " - " + arrival);
        labelKota.setBounds(270, -2, 400, 50);
        labelKota.setFont(new Font("SansSerif", Font.BOLD, 25));
        // Make label text readable against any background
        labelKota.setForeground(Color.BLACK);
        // Optional: add a subtle background to the label for better readability
        labelKota.setOpaque(false);
        mainPanel.add(labelKota);

        backButton = new JButton("Kembali");
        backButton.setBounds(680, 10, 90, 25);
        backButton.setVisible(false);
        styleButton(backButton);
        mainPanel.add(backButton);


        backButton.addActionListener(e -> {
            if (isFillingForm) {
                removePassengerPanel();
                isFillingForm = false;

                for (JButton seat : allSeats) {
                    if(!seat.getBackground().equals(seatReservedColor) &&
                            !seat.getBackground().equals(seatOverlapColor)) {
                        seat.setEnabled(true);

                        if(selectedSeats.contains(seat.getText())) {
                            seat.setBackground(darkBlue);
                            seat.setForeground(Color.WHITE);
                        } else {
                            seat.setBackground(seatPanelColor);
                            seat.setForeground(Color.BLACK);
                        }
                    }
                }
                updatePriceLabel();
                backButton.setVisible(false);
                nextButton.setVisible(true);
                resetButton.setVisible(true);
                isFillingForm = false;
            }
        });

        JPanel seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBackground(beige);
        JScrollPane seatScrollPane = new JScrollPane(seatPanel);
        seatScrollPane.setBounds(270, 50, 500, 620);
        mainPanel.add(seatScrollPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 40, 0);
        gbc.gridy = 0;
        gbc.gridx = 3;
        JLabel driverSeat = new JLabel("Driver", SwingConstants.CENTER);
        driverSeat.setOpaque(true);
        driverSeat.setBackground(darkBlue);
        driverSeat.setForeground(Color.WHITE);
        driverSeat.setFont(new Font("SansSerif", Font.BOLD, 13));
        driverSeat.setPreferredSize(new Dimension(60, 40));
        seatPanel.add(driverSeat, gbc);
        gbc.insets = new Insets(2, 5, 2, 5);

        String[] leftSeats = {
                "A1", "A2", "A3", "A4", "A5", "A6",
                "A7", "A8", "A9", "A10", "A11", "A12",
                "A13", "A14", "A15", "A16", "A17", "A18"
        };

        String[] rightSeats = {
                "B1", "B2", "B3", "B4", "B5", "B6",
                "B7", "B8", "B9", "B10", "B11", "B12",
                "B13", "B14", "B15", "B16", "B17", "B18",
                "B19", "B20", "B21", "B22"
        };

        int leftIndex = 0, rightIndex = 0;
        for (int i = 2; i < 24; i++) {
            gbc.gridy = i;

            gbc.gridx = 0;
            if (leftIndex < leftSeats.length)
                seatPanel.add(createSeat(leftSeats[leftIndex++]), gbc);

            gbc.gridx = 1;
            if (leftIndex < leftSeats.length)
                seatPanel.add(createSeat(leftSeats[leftIndex++]), gbc);

            gbc.gridx = 2;
            seatPanel.add(Box.createHorizontalStrut(30), gbc);

            gbc.gridx = 3;
            if (rightIndex < rightSeats.length)
                seatPanel.add(createSeat(rightSeats[rightIndex++]), gbc);

            gbc.gridx = 4;
            if (rightIndex < rightSeats.length)
                seatPanel.add(createSeat(rightSeats[rightIndex++]), gbc);
        }

        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        JButton toiletButton = new JButton("Toilet");
        toiletButton.setEnabled(false);
        toiletButton.setBackground(Color.LIGHT_GRAY);
        toiletButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        toiletButton.setPreferredSize(new Dimension(100, 35));
        seatPanel.add(toiletButton, gbc);

        nextButton = new JButton("Lanjut");
        resetButton = new JButton("Reset");
        pricept = new JLabel("Rp0");
        nextButton.setBounds(270, 680, 100, 30);
        resetButton.setBounds(380, 680, 100, 30);
        pricept.setBounds(500, 680, 200, 30);
        pricept.setFont(new Font("SansSerif", Font.BOLD, 16));
        pricept.setForeground(darkBlue);
        pricept.setOpaque(true);
        pricept.setBackground(new Color(255, 255, 255, 255));
        styleButton(nextButton);
        styleButton(resetButton);
        mainPanel.add(nextButton);
        mainPanel.add(resetButton);
        mainPanel.add(pricept);

        // Tambahkan tombol kembali (untuk menutup frame)
        kembaliButton = new JButton("Halaman Sebelumnya");
        kembaliButton.setBounds(33, 715, 200, 30);
        styleButton(kembaliButton);
        mainPanel.add(kembaliButton);
        kembaliButton.addActionListener(e -> {
            dispose(); // Tutup MainFrame
            SwingUtilities.invokeLater(Trayek::new); // Buka kembali CityFrame
        });

        historyButton = new JButton("Riwayat Pemesanan");
        historyButton.setBounds(570, 715, 200, 30);
        styleButton(historyButton);
        mainPanel.add(historyButton);
        historyButton.addActionListener(e -> {
            SwingUtilities.invokeLater(History::new);
        });

        nextButton.addActionListener(e -> {
            if (!isFillingForm && !selectedSeats.isEmpty()) {
                showPassengerForm();
                isFillingForm = true;
                for (JButton seat : allSeats){
                    if(seat.isEnabled() && !selectedSeats.contains(seat.getText())){
                        seat.setEnabled(false);
                    }
                }
                backButton.setVisible(true);
                nextButton.setVisible(false);
                resetButton.setVisible(false);
            }
        });

        resetButton.addActionListener(e -> {
            for (JButton seat : allSeats) {
                // Only reset seats that are not already booked (gray) or overlapping routes (red)
                if (!seat.getBackground().equals(seatReservedColor) &&
                        !seat.getBackground().equals(seatOverlapColor)) {
                    seat.setBackground(seatPanelColor);
                    seat.setEnabled(true);
                    seat.setForeground(Color.BLACK);
                }
            }
            selectedSeats.clear();
            removePassengerPanel();
            updatePriceLabel();
        });

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(darkBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void updatePriceLabel() {
        pricept.setText(String.format("Rp%,.2f", (price * selectedSeats.size())));
    }

    private JButton createSeat(String label) {
        JButton seatButton = new JButton(label);
        seatButton.setPreferredSize(new Dimension(60, 40));
        seatButton.setBackground(seatPanelColor);

        seatButton.addActionListener(e -> {
            if (seatButton.getBackground().equals(seatPanelColor)) {
                seatButton.setBackground(darkBlue);
                seatButton.setForeground(Color.WHITE);
                selectedSeats.add(label);
            } else if (seatButton.getBackground().equals(darkBlue)) {
                seatButton.setBackground(seatPanelColor);
                seatButton.setForeground(Color.BLACK);
                selectedSeats.remove(label);
            }
            updatePriceLabel();
        });

        allSeats.add(seatButton);
        seatButtonMap.put(label, seatButton);
        return seatButton;
    }

    private void showPassengerForm() {
        removePassengerPanel();

        passengerPanel = new JPanel();
        passengerPanel.setLayout(new BoxLayout(passengerPanel, BoxLayout.Y_AXIS));
        passengerPanel.setBackground(beige);

        List<JTextField> nameFields = new ArrayList<>();
        List<JTextField> nikFields = new ArrayList<>();
        List<JTextField> phoneFields = new ArrayList<>();
        List<String> seatList = new ArrayList<>(selectedSeats);

        int counter = 1;

        for (String seat : seatList) {
            JPanel singlePassengerPanel = new JPanel(null);
            singlePassengerPanel.setPreferredSize(new Dimension(240, 160));
            singlePassengerPanel.setBorder(BorderFactory.createTitledBorder("Seat " + seat));
            singlePassengerPanel.setBackground(beige);

            JLabel nameLabel = new JLabel("Nama Penumpang " + counter + ":");
            nameLabel.setBounds(10, 20, 140, 25);
            JTextField nameField = new JTextField();
            nameField.setBounds(150, 20, 80, 25);

            JLabel nikLabel = new JLabel("NIK Penumpang " + counter + ":");
            nikLabel.setBounds(10, 50, 140, 25);
            JTextField nikField = new JTextField();
            nikField.setBounds(150, 50, 80, 25);

            JLabel phoneLabel = new JLabel("No. Telp Penumpang " + counter + ":");
            phoneLabel.setBounds(10, 80, 160, 25);
            JTextField phoneField = new JTextField();
            phoneField.setBounds(150, 80, 80, 25);

            nameFields.add(nameField);
            nikFields.add(nikField);
            phoneFields.add(phoneField);

            singlePassengerPanel.add(nameLabel);
            singlePassengerPanel.add(nameField);
            singlePassengerPanel.add(nikLabel);
            singlePassengerPanel.add(nikField);
            singlePassengerPanel.add(phoneLabel);
            singlePassengerPanel.add(phoneField);

            passengerPanel.add(singlePassengerPanel);
            counter++;
        }

        JButton orderAllButton = new JButton("Pesan Semua");
        orderAllButton.setMaximumSize(new Dimension(200, 30));
        orderAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(orderAllButton);

        orderAllButton.addActionListener(ev -> {
            for (int i = 0; i < seatList.size(); i++) {
                String name = nameFields.get(i).getText().trim();
                String nik = nikFields.get(i).getText().trim();
                String phone = phoneFields.get(i).getText().trim();

                if (name.isEmpty() || nik.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Data penumpang untuk kursi " + seatList.get(i) + " belum lengkap!");
                    return;
                }
                if (nik.length() != 16 || !nik.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "NIK harus 16 digit angka untuk kursi " + seatList.get(i));
                    return;
                }
                if (!phone.matches("\\d{10,13}")) {
                    JOptionPane.showMessageDialog(this, "No. Telp tidak valid untuk kursi " + seatList.get(i));
                    return;
                }
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < seatList.size(); i++) {
                String seat = seatList.get(i);
                String name = nameFields.get(i).getText().trim();
                String nik = nikFields.get(i).getText().trim();
                String phone = phoneFields.get(i).getText().trim();

                sb.append(seat)
                    .append(" , ").append(name)
                    .append(" , ").append(nik)
                    .append(" , ").append(phone)
                    .append(" , ").append(departure)
                    .append(" , ").append(arrival)
                    .append(" , ").append(price)
                    .append("\n");
            }

            passenger.writeDataToFile(sb.toString(), "data_penumpang.txt");

            for (String seat : seatList) {
                JButton seatButton = seatButtonMap.get(seat);
                if (seatButton != null) {
                    seatButton.setBackground(seatReservedColor);
                    seatButton.setEnabled(false);
                }
            }

            JOptionPane.showMessageDialog(this, "Semua kursi berhasil dipesan!");
            selectedSeats.clear();
            removePassengerPanel();
            backButton.setVisible(false);
            nextButton.setVisible(true);
            resetButton.setVisible(true);
            isFillingForm = false;
            updatePriceLabel();

            for (JButton seat : allSeats) {
                if(!seat.getBackground().equals(seatReservedColor) &&
                        !seat.getBackground().equals(seatOverlapColor)) {
                    seat.setEnabled(true);
                    seat.setBackground(seatPanelColor);
                    seat.setForeground(Color.BLACK);
                }
            }
        });

        passengerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        passengerPanel.add(orderAllButton);

        passengerScrollPane = new JScrollPane(passengerPanel);
        passengerScrollPane.setBounds(10, 10, 250, 700);
        mainPanel.add(passengerScrollPane);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void removePassengerPanel() {
        if (passengerScrollPane != null) {
            mainPanel.remove(passengerScrollPane);
            passengerScrollPane = null;
        }
        mainPanel.revalidate();
        mainPanel.repaint();
        isFillingForm = false;
    }

    public MainFrame(double price, String departure, String arrival) {
        this(); // Panggil constructor default
        this.price = price;
        this.departure = departure;
        this.arrival = arrival;

        // Update label kota setelah nilainya tersedia
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().equals("null - null")) {
                    label.setText(departure + " - " + arrival);
                }
            }
        }

        updatePriceLabel();

        // Make sure the GUI is fully rendered before loading seats
        SwingUtilities.invokeLater(() -> {
            // Load booked seats from file
            loadBookedSeats();
        });
    }

    // Add method to load booked seats
    private void loadBookedSeats() {
        try {
            // Make sure the data directory exists
            File dataDir = new File("datas");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
                System.out.println("Created data directory: " + dataDir.getAbsolutePath());
            }

            // Check if data file exists
            File dataFile = new File("datas/data_penumpang.txt");
            if (!dataFile.exists()) {
                // Create the file if it doesn't exist
                dataFile.createNewFile();
                System.out.println("Created new data file: " + dataFile.getAbsolutePath());
                return; // No data to load yet
            }

            System.out.println("Loading booked seats from: " + dataFile.getAbsolutePath());

            // Read booked seats data
            String[] bookedSeatsData = passenger.readDataFromFile("data_penumpang.txt");

            if (bookedSeatsData == null || bookedSeatsData.length == 0) {
                System.out.println("No booked seats data found");
                return;
            }

            System.out.println("Found " + bookedSeatsData.length + " booking records");

            // First pass: Find directly booked seats for this route
            for (String bookingData : bookedSeatsData) {
                if (bookingData == null || bookingData.trim().isEmpty()) continue;

                String[] parts = bookingData.split(" , ");
                if (parts.length >= 6) {
                    String seatNumber = parts[0].trim();
                    String bookedDeparture = parts[4].trim();
                    String bookedArrival = parts[5].trim();

                    // Check if this booking exactly matches the current route
                    if (bookedDeparture.equals(departure) && bookedArrival.equals(arrival)) {
                        // Mark seat as booked
                        JButton seatButton = seatButtonMap.get(seatNumber);
                        if (seatButton != null) {
                            System.out.println("Marking seat " + seatNumber + " as booked for exact match");
                            seatButton.setBackground(seatReservedColor);
                            seatButton.setEnabled(false);
                            seatButton.setForeground(Color.WHITE);
                        }
                    }
                }
            }

            // Second pass: Check for overlapping routes
            for (JButton seat : allSeats) {
                String seatNumber = seat.getText();

                // Skip seats that are already marked as booked for this route
                if (seat.getBackground().equals(seatReservedColor)) {
                    continue;
                }

                // Get all bookings for this seat
                List<Passenger.Booking> seatBookings = passenger.getBookingsForSeat(seatNumber);

                // Check if any booking overlaps with current route
                for (Passenger.Booking booking : seatBookings) {
                    // Check if routes overlap
                    if (passenger.routesOverlap(departure, arrival,
                            booking.departure, booking.arrival)) {
                        System.out.println("Marking seat " + seatNumber + " as unavailable due to overlapping route: " +
                                booking.departure + " to " + booking.arrival);
                        seat.setBackground(seatOverlapColor);
                        seat.setEnabled(false);
                        seat.setForeground(Color.WHITE);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading booked seats: " + e.getMessage());
            e.printStackTrace();
        }
    }
}