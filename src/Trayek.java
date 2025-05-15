import javax.swing.*;
import java.awt.*;

public class Trayek extends JFrame {
    final Image ICON = new ImageIcon("assets/logo.png").getImage();

    private final Color darkBlue = new Color(0x27548A);
    private final Color seatPanelColor = new Color(0x61A2CB);
    private final Color beige = new Color(0xECDFBA);

    public int harga;
    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 500;
    Image logo, bgKota;
    int xVelocity = 1;
    int yVelocity = 1;
    int x = 0;
    int y = 0;

    public Trayek() {
        departureArrival();
    }

    private void departureArrival() {
        setTitle("Departure Arrival");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setIconImage(ICON);
        setLocationRelativeTo(null);
        setResizable(false);

        // Muat gambar-gambar
        logo = new ImageIcon("assets/icon.png").getImage();
        bgKota = new ImageIcon("assets/kota.png").getImage();
        int imageWidth = logo.getWidth(null);

        // Hitung posisi logo
        x = (PANEL_WIDTH - imageWidth) / 2 + 50;
        y = 0;

        // Buat panel latar belakang kustom dengan warna beige
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(beige); // Tetap gunakan warna beige
        setContentPane(backgroundPanel);

        // Buat panel formulir dan tambahkan ke panel latar belakang
        JPanel dptArrivalPanel = createFormPanel();
        backgroundPanel.add(dptArrivalPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createFormPanel() {
        String[] cities = {
                "MAGELANG", "JOGJA", "KARTOSURO", "SOLO",
                "GENDINGAN", "NGAWI", "WILANGAN"
        };

        double[][] fares = {
                { 0, 15000, 30000, 30000, 55000, 60000, 85000 },
                { 0, 0, 15000, 15000, 40000, 45000, 70000 },
                { 0, 0, 0, 15000, 35000, 40000, 60000 },
                { 0, 0, 0, 0, 25000, 30000, 55000 },
                { 0, 0, 0, 0, 0, 25000, 45000 },
                { 0, 0, 0, 0, 0, 0, 30000 },
                { 0, 0, 0, 0, 0, 0, 0 }
        };

        // Panel formulir dengan warna transparan agar gambar latar tetap terlihat
        JPanel dptArrivalPanel = new JPanel();
        dptArrivalPanel.setOpaque(false); // Panel transparan

        // Gunakan BoxLayout untuk penempatan vertikal yang lebih baik
        dptArrivalPanel.setLayout(new BoxLayout(dptArrivalPanel, BoxLayout.Y_AXIS));

        // Tambahkan ruang kosong di atas agar tidak bertabrakan dengan logo
        dptArrivalPanel.add(Box.createVerticalStrut(150));

        // Buat panel untuk form dengan GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // Jadikan transparan juga
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tambahkan komponen-komponen form
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel dptLabel = new JLabel("Departure:");
        formPanel.add(dptLabel, gbc);

        gbc.gridy = 1;
        JComboBox<String> dptCombo = new JComboBox<>(cities);
        formPanel.add(dptCombo, gbc);

        gbc.gridy = 2;
        JLabel arrLabel = new JLabel("Arrival:");
        formPanel.add(arrLabel, gbc);

        gbc.gridy = 3;
        JComboBox<String> arrCombo = new JComboBox<>(cities);
        formPanel.add(arrCombo, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 10, 10); // Tambahkan ruang di atas tombol
        JButton submitButton = new JButton("Submit");
        styleButton(submitButton);
        formPanel.add(submitButton, gbc);

        // Tambahkan panel form ke panel utama
        dptArrivalPanel.add(formPanel);

        // Tambahkan ruang fleksibel setelah form
        dptArrivalPanel.add(Box.createVerticalGlue());

        submitButton.addActionListener(e -> {
            String departure = (String) dptCombo.getSelectedItem();
            String arrival = (String) arrCombo.getSelectedItem();

            int i = indexOfCity(departure);
            int j = indexOfCity(arrival);

            if (i == j) {
                JOptionPane.showMessageDialog(this, "Kota asal dan tujuan tidak boleh sama.");
                return;
            }

            double harga = fares[Math.min(i, j)][Math.max(i, j)];

            // Kirim harga ke MainFrame melalui constructor yang benar
            MainFrame mainFrame = new MainFrame(harga, departure, arrival);
            mainFrame.setVisible(true);
            this.dispose();
        });

        return dptArrivalPanel;
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

    // Panel kustom untuk menggambar latar belakang dan logo
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D) g;

            // Pastikan warna latar belakang digambar dulu
            g2D.setColor(beige);
            g2D.fillRect(0, 0, getWidth(), getHeight());

            // Gambar logo di posisinya
            g2D.drawImage(logo, x, y, null);

            // Gambar latar belakang kota di bawah
            int bgHeight = bgKota.getHeight(null);
            int bgY = getHeight() - bgHeight;
            g2D.drawImage(bgKota, 0, bgY, null);
        }
    }
}