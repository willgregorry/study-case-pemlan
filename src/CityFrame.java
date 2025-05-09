import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class CityFrame extends JFrame {
    private final Color beige = new Color(0xECDFBA);  // Warna beige yang diinginkan untuk panel
    public CityFrame() {

        setTitle("Departure Arrival");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);


        JPanel dptArrivalPanel = new JPanel(new GridBagLayout());
        dptArrivalPanel.setBackground(beige); // Pastikan 'beige' didefinisikan sebelumnya
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spasi antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label "Departure"
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel label = new JLabel("Departure");
        dptArrivalPanel.add(label, gbc);

        // TextField untuk Departure
        gbc.gridx = 0;
        gbc.gridy = 2;
        JTextField dpt = new JTextField(15);
        dptArrivalPanel.add(dpt, gbc);

        // Label "Arrival"
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel label2 = new JLabel("Arrival");
        dptArrivalPanel.add(label2, gbc);

        // TextField untuk Arrival
        gbc.gridx = 0;
        gbc.gridy = 4;
        JTextField arrival = new JTextField(15);
        dptArrivalPanel.add(arrival, gbc);

        setContentPane(dptArrivalPanel);
        setVisible(true);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(CityFrame::new);
    }
}
