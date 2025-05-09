import javax.swing.*;
import java.awt.*;

public class CityFrame extends JFrame {
    private final Color beige = new Color(0xECDFBA);  // Warna beige
    public int harga;
    MainFrame mainFrame = new MainFrame (false) ;
    public CityFrame() {
        departureArrival();
    }

    private void departureArrival() {
        setTitle("Departure Arrival");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        String[] cities = {
                "WILANGAN", "NGAWI", "GENDINGAN", "SOLO",
                "KARTOSURO", "JOGJA", "MAGELANG"
        };

        int[][] fares = {
                {85000, 60000, 55000, 30000, 30000, 15000, 0},    // MAGELANG
                {70000, 45000, 40000, 15000, 15000, 0,     0},    // JOGJA
                {60000, 40000, 35000, 15000, 0,     0,     0},    // KARTOSURO
                {55000, 30000, 25000, 0,     0,     0,     0},    // SOLO
                {45000, 25000, 0,     0,     0,     0,     0},    // GENDINGAN
                {35000, 0,     0,     0,     0,     0,     0},    // NGAWI
                {0,     0,     0,     0,     0,     0,     0}     // WILANGAN
        };

        JPanel dptArrivalPanel = new JPanel(new GridBagLayout());
        dptArrivalPanel.setBackground(beige);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label Departure
        gbc.gridx = 0;
        gbc.gridy = 0;
        dptArrivalPanel.add(new JLabel("Departure:"), gbc);

        // ComboBox Departure
        gbc.gridy = 1;
        JComboBox<String> dptCombo = new JComboBox<>(cities);
        dptArrivalPanel.add(dptCombo, gbc);

        // Label Arrival
        gbc.gridy = 2;
        dptArrivalPanel.add(new JLabel("Arrival:"), gbc);

        // ComboBox Arrival
        gbc.gridy = 3;
        JComboBox<String> arrCombo = new JComboBox<>(cities);
        dptArrivalPanel.add(arrCombo, gbc);

        // Tombol Submit
        gbc.gridy = 4;
        JButton submitButton = new JButton("Submit");
        dptArrivalPanel.add(submitButton, gbc);


        setContentPane(dptArrivalPanel);
        setVisible(true);

        submitButton.addActionListener(e -> {
            String departure = (String) dptCombo.getSelectedItem();
            String arrival = (String) arrCombo.getSelectedItem();

            int i = indexOfCity(departure);
            int j = indexOfCity(arrival);

            if (i == j) {
                JOptionPane.showMessageDialog(this, "Kota asal dan tujuan tidak boleh sama.");
                return;
            }

            harga = fares[Math.min(i, j)][Math.max(i, j)];
            mainFrame.setPrice(harga);
            // Buka MainFrame dan kirim harga
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);

            this.dispose();
        });
    }

    private int indexOfCity(String city) {
        return switch (city) {
            case "WILANGAN" -> 6;
            case "NGAWI" -> 5;
            case "GENDINGAN" -> 4;
            case "SOLO" -> 3;
            case "KARTOSURO" -> 2;
            case "JOGJA" -> 1;
            case "MAGELANG" -> 0;
            default -> -1;
        };
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(CityFrame::new);
    }
}
