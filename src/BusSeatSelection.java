import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BusSeatSelection extends JFrame {

    private final List<JButton> allSeats = new ArrayList<>();
    private final Set<String> selectedSeats = new LinkedHashSet<>();
    private final Map<String, JButton> seatButtonMap = new HashMap<>();
    private JPanel passengerPanel;
    private JScrollPane passengerScrollPane;
    private JPanel mainPanel;
    private JButton backButton, nextButton, resetButton;
    private boolean isFillingForm = false;

    private final Color darkBlue = new Color(0x27548A);
    private final Color seatPanelColor = new Color(0x7AE2CF); // Warna kursi diubah menjadi 7AE2CF
    private final Color beige = new Color(0xECDFBA);  // Warna beige yang diinginkan untuk panel


    public BusSeatSelection() {
        setTitle("Pilih Kursi Bus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE); // Mengubah warna main panel menjadi putih
        setContentPane(mainPanel);

        backButton = new JButton("Kembali");
        backButton.setBounds(680, 10, 90, 25);
        backButton.setVisible(false);
        styleButton(backButton);
        mainPanel.add(backButton);
        backButton.addActionListener(e -> {
            if (isFillingForm) {
                removePassengerPanel();
                selectedSeats.clear();
                for (JButton seat : allSeats) {
                    if (seat.isEnabled()) {
                        seat.setBackground(seatPanelColor);
                        seat.setForeground(Color.BLACK);
                    }
                }
                backButton.setVisible(false);
                nextButton.setVisible(true);
                resetButton.setVisible(true);
                isFillingForm = false;
            }
        });

        JPanel seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBackground(beige); // Panel kursi menggunakan warna beige
        JScrollPane seatScrollPane = new JScrollPane(seatPanel);
        seatScrollPane.setBounds(270, 50, 500, 620);
        mainPanel.add(seatScrollPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 40, 0);
        gbc.gridy = 0;
        gbc.gridx = 3;
        JButton driverSeat = new JButton("Driver");
        driverSeat.setEnabled(false);
        driverSeat.setBackground(Color.BLACK);
        driverSeat.setForeground(Color.WHITE);
        driverSeat.setFont(new Font("SansSerif", Font.PLAIN, 10));
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
        nextButton.setBounds(270, 680, 100, 30);
        resetButton.setBounds(380, 680, 100, 30);
        styleButton(nextButton);
        styleButton(resetButton);
        mainPanel.add(nextButton);
        mainPanel.add(resetButton);

        nextButton.addActionListener(e -> {
            if (!isFillingForm && !selectedSeats.isEmpty()) {
                showPassengerForm();
                isFillingForm = true;
                backButton.setVisible(true);
                nextButton.setVisible(false);
                resetButton.setVisible(false);
            }
        });

        resetButton.addActionListener(e -> {
            for (JButton seat : allSeats) {
                seat.setBackground(seatPanelColor);
                seat.setEnabled(true);
                seat.setForeground(Color.BLACK);
            }
            selectedSeats.clear();
            removePassengerPanel();
        });

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(darkBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private JButton createSeat(String label) {
        JButton seatButton = new JButton(label);
        seatButton.setPreferredSize(new Dimension(60, 40));
        seatButton.setBackground(seatPanelColor); // Warna dasar kursi adalah 7AE2CF

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
        });

        allSeats.add(seatButton);
        seatButtonMap.put(label, seatButton);
        return seatButton;
    }

    private void showPassengerForm() {
        removePassengerPanel();

        passengerPanel = new JPanel();
        passengerPanel.setLayout(new BoxLayout(passengerPanel, BoxLayout.Y_AXIS));
        passengerPanel.setBackground(beige); // Panel isi data menggunakan beige #ECDFBA

        List<JTextField> nameFields = new ArrayList<>();
        List<JTextField> nikFields = new ArrayList<>();
        List<JTextField> phoneFields = new ArrayList<>();
        List<String> seatList = new ArrayList<>(selectedSeats);

        int counter = 1;

        for (String seat : seatList) {
            JPanel singlePassengerPanel = new JPanel(null);
            singlePassengerPanel.setPreferredSize(new Dimension(240, 160));
            singlePassengerPanel.setBorder(BorderFactory.createTitledBorder("Seat " + seat));
            singlePassengerPanel.setBackground(beige); // Setiap panel penumpang menggunakan beige #ECDFBA

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
            }

            for (int i = 0; i < seatList.size(); i++) {
                String seat = seatList.get(i);
                JButton seatButton = seatButtonMap.get(seat);
                if (seatButton != null) {
                    seatButton.setBackground(Color.GRAY);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BusSeatSelection::new);
    }
}