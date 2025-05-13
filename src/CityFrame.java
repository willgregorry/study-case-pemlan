import javax.swing.*;
import java.awt.*;

public class CityFrame extends JFrame {
    private final Color beige = new Color(0xECDFBA);  // Warna beige
    public int harga;

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
                "MAGELANG", "JOGJA", "KARTOSURO", "SOLO",
                "GENDINGAN", "NGAWI", "WILANGAN"
        };

        int[][] fares = {
                {0,     15000, 30000, 30000, 55000, 60000, 85000},
                {0,     0,     15000, 15000, 40000, 45000, 70000},
                {0,     0,     0,     15000, 35000, 40000, 60000},
                {0,     0,     0,     0,     25000, 30000, 55000},
                {0,     0,     0,     0,     0,     25000, 45000},
                {0,     0,     0,     0,     0,     0,     30000},
                {0,     0,     0,     0,     0,     0,     0}
        };

        JPanel dptArrivalPanel = new JPanel(new GridBagLayout());
        dptArrivalPanel.setBackground(beige);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        dptArrivalPanel.add(new JLabel("Departure:"), gbc);

        gbc.gridy = 1;
        JComboBox<String> dptCombo = new JComboBox<>(cities);
        dptArrivalPanel.add(dptCombo, gbc);

        gbc.gridy = 2;
        dptArrivalPanel.add(new JLabel("Arrival:"), gbc);

        gbc.gridy = 3;
        JComboBox<String> arrCombo = new JComboBox<>(cities);
        dptArrivalPanel.add(arrCombo, gbc);

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
