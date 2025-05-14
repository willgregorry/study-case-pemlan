import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class History extends JFrame {
    private final Image ICON = new ImageIcon("assets/icon.png").getImage();
    private final Color darkBlue = new Color(0x27548A);
    private final Color beige = new Color(0xECDFBA);
    private final FileManager fileManager = new FileManager();
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private double harga;

    public History() {
        initializeFrame();
        setupComponents();
        loadBookingHistory();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Riwayat Pemesanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setIconImage(ICON);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void setupComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(beige);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(beige);
        JLabel titleLabel = new JLabel("Riwayat Pemesanan Tiket Bus");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(darkBlue);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Table setup
        String[] columnNames = {"No. Kursi", "Nama Penumpang", "NIK", "No. Telepon", "Keberangkatan", "Tujuan"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setFillsViewportHeight(true);
        bookingTable.setRowHeight(25);
        bookingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        bookingTable.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Set column widths
        bookingTable.getColumnModel().getColumn(0).setPreferredWidth(70);    // Seat
        bookingTable.getColumnModel().getColumn(1).setPreferredWidth(150);   // Name
        bookingTable.getColumnModel().getColumn(2).setPreferredWidth(120);   // NIK
        bookingTable.getColumnModel().getColumn(3).setPreferredWidth(120);   // Phone
        bookingTable.getColumnModel().getColumn(4).setPreferredWidth(100);   // Departure
        bookingTable.getColumnModel().getColumn(5).setPreferredWidth(100);   // Arrival

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(darkBlue));

        // Button panel at bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(beige);

        JButton refreshButton = new JButton("Refresh Data");
        styleButton(refreshButton);
        refreshButton.addActionListener(e -> loadBookingHistory());

        JButton backButton = new JButton("Kembali");
        styleButton(backButton);
        backButton.addActionListener(e -> {
            dispose(); // Close this frame
        });

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the content pane
        setContentPane(mainPanel);
    }

    private void loadBookingHistory() {
        // Clear existing table data
        tableModel.setRowCount(0);

        try {
            // Read booking data from file
            String[] bookingData = fileManager.readDataFromFile("data_penumpang.txt");

            if (bookingData == null || bookingData.length == 0) {
                System.out.println("No booking data found");
                JOptionPane.showMessageDialog(this, "Tidak ada data pemesanan yang ditemukan.",
                        "Data Kosong", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Add data to table
            for (String data : bookingData) {
                if (data == null || data.trim().isEmpty()) continue;

                String[] parts = data.split(" , ");
                if (parts.length >= 6) {
                    String seatNumber = parts[0].trim();
                    String name = parts[1].trim();
                    String nik = parts[2].trim();
                    String phone = parts[3].trim();
                    String departure = parts[4].trim();
                    String arrival = parts[5].trim();

                    tableModel.addRow(new Object[]{seatNumber, name, nik, phone, departure, arrival});
                }
            }

            System.out.println("Loaded " + tableModel.getRowCount() + " booking records");

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Tidak ada data pemesanan yang valid.",
                        "Data Kosong", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("Error loading booking data: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data pemesanan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(darkBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    // Main method for testing
    // public static void main(String[] args) {
    //     // Set look and feel to system default
    //     try {
    //         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     SwingUtilities.invokeLater(Wilgay::new);
    // }
}