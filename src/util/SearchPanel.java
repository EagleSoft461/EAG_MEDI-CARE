package util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchPanel extends JPanel {
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable table;

    public SearchPanel(JTable table) {
        this.table = table;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Arama alanı
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.putClientProperty("JTextField.placeholderText", "🔍 Ara...");

        // Sorter'ı başlat
        if (table.getModel() instanceof DefaultTableModel) {
            sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
            table.setRowSorter(sorter);
        }

        // Arama dinleyicisi
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Temizle butonu
        JButton clearButton = new JButton("Temizle");
        clearButton.addActionListener(e -> {
            searchField.setText("");
            sorter.setRowFilter(null);
        });

        add(searchField, BorderLayout.CENTER);
        add(clearButton, BorderLayout.EAST);
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public void clearSearch() {
        searchField.setText("");
        if (sorter != null) {
            sorter.setRowFilter(null);
        }
    }
}