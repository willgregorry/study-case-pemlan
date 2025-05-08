import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BusSeatSelection extends JFrame {

    private List<JButton> allSeats = new ArrayList<>();

    public BusSeatSelection() {
        setTitle("Pilih Kursi Bus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(null); // null layout utama
        setContentPane(mainPanel);

        // Passenger Data Panel
        JPanel passengerPanel = new JPanel(null);
        passengerPanel.setBounds(10, 10, 250, 300);
        passengerPanel.setBorder(BorderFactory.createTitledBorder("Data Penumpang"));
        mainPanel.add(passengerPanel);

        JLabel nameLabel = new JLabel("Nama:");
        nameLabel.setBounds(10, 20, 100, 25);
        passengerPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(10, 45, 200, 25);
        passengerPanel.add(nameField);

        JLabel idLabel = new JLabel("NIK:");
        idLabel.setBounds(10, 80, 100, 25);
        passengerPanel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(10, 105, 200, 25);
        passengerPanel.add(idField);

        JLabel phoneLabel = new JLabel("No. Telepon:");
        phoneLabel.setBounds(10, 140, 100, 25);
        passengerPanel.add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setBounds(10, 165, 200, 25);
        passengerPanel.add(phoneField);

        JButton orderButton = new JButton("Pesan");
        orderButton.setBounds(10, 200, 100, 25);
        passengerPanel.add(orderButton);
        orderButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Berhasil memesan");
            nameField.setText("");
            idField.setText("");
            phoneField.setText("");
        });

        // Back Button
        JButton backButton = new JButton("Kembali");
        backButton.setBounds(680, 10, 90, 25);
        mainPanel.add(backButton);
        backButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Kembali ke menu utama");
        });

        // Seat Panel
        JPanel seatPanel = new JPanel(new GridBagLayout());
        JScrollPane seatScrollPane = new JScrollPane(seatPanel);
        seatScrollPane.setBounds(270, 50, 500, 620);
        mainPanel.add(seatScrollPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);

        // Driver Seat
        gbc.gridy = 0;
        gbc.gridx = 3;
        gbc.insets = new Insets(0, 0, 40, 0);
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
                seatPanel.add(createSeat(leftSeats[leftIndex++], new Dimension(60, 40)), gbc);

            gbc.gridx = 1;
            if (leftIndex < leftSeats.length)
                seatPanel.add(createSeat(leftSeats[leftIndex++], new Dimension(60, 40)), gbc);

            gbc.gridx = 2;
            seatPanel.add(Box.createHorizontalStrut(30), gbc);

            gbc.gridx = 3;
            if (rightIndex < rightSeats.length)
                seatPanel.add(createSeat(rightSeats[rightIndex++], new Dimension(60, 40)), gbc);

            gbc.gridx = 4;
            if (rightIndex < rightSeats.length)
                seatPanel.add(createSeat(rightSeats[rightIndex++], new Dimension(60, 40)), gbc);
        }

        // Toilet
        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        JButton toiletButton = new JButton("Toilet");
        toiletButton.setEnabled(false);
        toiletButton.setBackground(Color.LIGHT_GRAY);
        toiletButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        toiletButton.setPreferredSize(new Dimension(100, 35));
        seatPanel.add(toiletButton, gbc);

        // Next and Reset Buttons
        JButton nextButton = new JButton("Lanjut");
        JButton resetButton = new JButton("Reset");
        nextButton.setBounds(270, 680, 100, 30);
        resetButton.setBounds(380, 680, 100, 30);
        mainPanel.add(nextButton);
        mainPanel.add(resetButton);

        resetButton.addActionListener(e -> {
            for (JButton seat : allSeats) {
                seat.setBackground(Color.GREEN);
            }
        });

        setVisible(true);
    }

    private JButton createSeat(String label, Dimension size) {
        JButton seatButton = new JButton(label);
        seatButton.setPreferredSize(size);
        seatButton.setBackground(Color.GREEN);
        seatButton.addActionListener(e -> {
            if (seatButton.getBackground() == Color.GREEN) {
                seatButton.setBackground(Color.RED);
            } else if (seatButton.getBackground() == Color.RED) {
                seatButton.setBackground(Color.GREEN);
            }
        });
        allSeats.add(seatButton);
        return seatButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BusSeatSelection::new);
    }
}
