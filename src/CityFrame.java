import javax.swing.*;
import java.awt.*;

public class CityFrame extends JFrame {
    final Image ICON = new ImageIcon("assets/icon.png").getImage();

    private final Color darkBlue = new Color(0x27548A);
    private final Color seatPanelColor = new Color(0x61A2CB);
    private final Color beige = new Color(0xECDFBA);

    public int harga;
    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 500;
    Image asu;
    Timer timer;
    int xVelocity = 1;
    int yVelocity = 1;
    int x = 0;
    int y = 0;


    public CityFrame() {
        departureArrival();
    }

    private void departureArrival() {
        setTitle("Departure Arrival");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setIconImage(ICON);
        setLocationRelativeTo(null);
        setResizable(false);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        asu = new ImageIcon("assets/icon.png").getImage();
        int imageWidth = asu.getWidth(null);

        // Atur posisi awal di atas tengah
        x = (PANEL_WIDTH - imageWidth) / 2 + 50;
        y = 0; // atau bisa digeser sedikit ke bawah

        String[] cities = {
                "MAGELANG", "JOGJA", "KARTOSURO", "SOLO",
                "GENDINGAN", "NGAWI", "WILANGAN"
        };

        int[][] fares = {
                { 0, 15000, 30000, 30000, 55000, 60000, 85000 },
                { 0, 0, 15000, 15000, 40000, 45000, 70000 },
                { 0, 0, 0, 15000, 35000, 40000, 60000 },
                { 0, 0, 0, 0, 25000, 30000, 55000 },
                { 0, 0, 0, 0, 0, 25000, 45000 },
                { 0, 0, 0, 0, 0, 0, 30000 },
                { 0, 0, 0, 0, 0, 0, 0 }
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
        styleButton(submitButton);

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

            int harga = fares[Math.min(i, j)][Math.max(i, j)];

            // Kirim harga ke MainFrame melalui constructor yang benar
            MainFrame mainFrame = new MainFrame(harga, departure, arrival); // <-- INI YANG BENAR
            mainFrame.setVisible(true);
            this.dispose();
        });

    }

    private int indexOfCity(String city) {
        switch (city) {
            case "WILANGAN":
                return 6;
            case "NGAWI":
                return 5;
            case "GENDINGAN":
                return 4;
            case "SOLO":
                return 3;
            case "KARTOSURO":
                return 2;
            case "JOGJA":
                return 1;
            case "MAGELANG":
                return 0;
            default:
                return -1;
        }
    }


    private void styleButton(JButton button) {
        button.setBackground(darkBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(asu, x, y, null);
    }

}
