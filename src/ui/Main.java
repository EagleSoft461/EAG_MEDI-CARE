package ui;

import data.DatabaseManager;
import config.DatabaseConfig;
import util.PrintUtility;
import util.SearchPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class Main extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private HashMap<String, JPanel> panels = new HashMap<>();

    // Modern renk paleti
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(51, 51, 51);
    private final Color SIDEBAR_COLOR = new Color(44, 62, 80);

    // Tablo modelleri
    private DefaultTableModel doktorTableModel;
    private DefaultTableModel hastaTableModel;
    private DefaultTableModel randevuTableModel;
    private DefaultTableModel poliklinikTableModel;
    private DefaultTableModel receteTableModel;

    // Tablo referansları
    private JTable doktorTable;
    private JTable hastaTable;
    private JTable randevuTable;
    private JTable poliklinikTable;
    private JTable receteTable;

    public Main() {
        initializeUI();
        setupDatabase();
        createSidebar();
        createContentArea();
        loadInitialData();
    }

    private void initializeUI() {
        setTitle(" Eag Hospital Management System");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDatabase() {
        initializeTableModels();

        try {
            DatabaseConfig.getConnection();
            System.out.println("✅ Veritabanı bağlantısı başarılı!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Veritabanı bağlantı hatası: " + e.getMessage(),
                    "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeTableModels() {
        doktorTableModel = new DefaultTableModel(
                new Object[]{"ID", "Doktor No", "Ad", "Soyad", "Uzmanlık", "Poliklinik", "Telefon", "E-posta", "Durum"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        hastaTableModel = new DefaultTableModel(
                new Object[]{"ID", "TC Kimlik", "Ad", "Soyad", "Doğum Tarihi", "Cinsiyet", "Telefon", "E-posta", "Kayıt Tarihi"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        randevuTableModel = new DefaultTableModel(
                new Object[]{"ID", "Hasta", "Doktor", "Poliklinik", "Randevu Tarihi", "Durum", "Açıklama"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        poliklinikTableModel = new DefaultTableModel(
                new Object[]{"ID", "Ad", "Açıklama", "Kat", "Telefon", "Doktor Sayısı"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        receteTableModel = new DefaultTableModel(
                new Object[]{"ID", "Hasta", "Doktor", "İlaç Adı", "Dozaj", "Kullanım", "Süre", "Reçete Tarihi"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        // Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(SIDEBAR_COLOR);
        JLabel logo = new JLabel("EAG MEDI-CARE");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logoPanel.add(logo);
        sidebar.add(logoPanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        // Menü butonları
        String[] menuItems = {
                " Dashboard", " Doktorlar", " Hastalar",
                " Randevular", " Poliklinikler", " Reçeteler"
        };

        String[] panelKeys = {"home", "doktor", "hasta", "randevu", "poliklinik", "recete"};

        for (int i = 0; i < menuItems.length; i++) {
            JButton menuButton = createModernMenuButton(menuItems[i]);
            String panelKey = panelKeys[i];
            menuButton.addActionListener(e -> {
                cardLayout.show(contentPanel, panelKey);
                refreshPanelData(panelKey);
            });
            sidebar.add(menuButton);
            sidebar.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        sidebar.add(Box.createVerticalGlue());
        add(sidebar, BorderLayout.WEST);
    }

    private JButton createModernMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(SIDEBAR_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(52, 73, 94));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(SIDEBAR_COLOR);
            }
        });

        return button;
    }

    private void createContentArea() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_COLOR);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerRight.setBackground(Color.WHITE);

        JLabel dateLabel = new JLabel(new SimpleDateFormat("dd MMMM yyyy, EEEE").format(new java.util.Date()));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(153, 153, 153));

        headerRight.add(dateLabel);

        header.add(title, BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);

        // Content panel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BACKGROUND_COLOR);

        // Panelleri oluştur
        panels.put("home", createDashboardPanel());
        panels.put("doktor", createDoctorsPanel());
        panels.put("hasta", createPatientsPanel());
        panels.put("randevu", createAppointmentsPanel());
        panels.put("poliklinik", createClinicsPanel());
        panels.put("recete", createPrescriptionsPanel());

        for (String key : panels.keySet()) {
            contentPanel.add(panels.get(key), key);
        }

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.add(header, BorderLayout.NORTH);
        mainContent.add(contentPanel, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);
    }

    // VERİTABANI ENTEGRASYONLU METODLAR
    private void refreshPanelData(String panelKey) {
        switch (panelKey) {
            case "home":
                updateDashboard();
                break;
            case "doktor":
                loadDoktorlar();
                break;
            case "hasta":
                loadHastalar();
                break;
            case "randevu":
                loadRandevular();
                break;
            case "poliklinik":
                loadPoliklinikler();
                break;
            case "recete":
                loadReceteler();
                break;
        }
    }

    private void loadInitialData() {
        refreshPanelData("home");
    }

    private void updateDashboard() {
        // Dashboard verilerini güncelle
    }

    private void loadDoktorlar() {
        doktorTableModel.setRowCount(0);
        List<Object[]> doktorlar = DatabaseManager.getDoktorlar();
        for (Object[] doktor : doktorlar) {
            doktorTableModel.addRow(doktor);
        }
    }

    private void loadHastalar() {
        hastaTableModel.setRowCount(0);
        List<Object[]> hastalar = DatabaseManager.getHastalar();
        for (Object[] hasta : hastalar) {
            // Tarih formatını düzenle
            if (hasta[4] instanceof Date) {
                Date date = (Date) hasta[4];
                hasta[4] = new SimpleDateFormat("dd.MM.yyyy").format(date);
            }
            if (hasta[8] instanceof Timestamp) {
                Timestamp ts = (Timestamp) hasta[8];
                hasta[8] = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(ts);
            }
            hastaTableModel.addRow(hasta);
        }
    }

    private void loadRandevular() {
        randevuTableModel.setRowCount(0);
        List<Object[]> randevular = DatabaseManager.getRandevular();
        for (Object[] randevu : randevular) {
            if (randevu[4] instanceof Timestamp) {
                Timestamp ts = (Timestamp) randevu[4];
                randevu[4] = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(ts);
            }
            randevuTableModel.addRow(randevu);
        }
    }

    private void loadPoliklinikler() {
        poliklinikTableModel.setRowCount(0);
        List<Object[]> poliklinikler = DatabaseManager.getPoliklinikler();
        for (Object[] poliklinik : poliklinikler) {
            poliklinikTableModel.addRow(poliklinik);
        }
    }

    private void loadReceteler() {
        receteTableModel.setRowCount(0);
        List<Object[]> receteler = DatabaseManager.getReceteler();
        for (Object[] recete : receteler) {
            if (recete[7] instanceof Date) {
                Date date = (Date) recete[7];
                recete[7] = new SimpleDateFormat("dd.MM.yyyy").format(date);
            }
            receteTableModel.addRow(recete);
        }
    }

    // PANEL OLUŞTURMA METODLARI
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        statsPanel.setBackground(BACKGROUND_COLOR);

        // Gerçek verilerle istatistik kartları
        statsPanel.add(createStatCard("Toplam Doktor",
                String.valueOf(DatabaseManager.getToplamDoktor()), " ️", PRIMARY_COLOR));
        statsPanel.add(createStatCard("Toplam Hasta",
                String.valueOf(DatabaseManager.getToplamHasta()), "", ACCENT_COLOR));
        statsPanel.add(createStatCard("Bugünkü Randevu",
                String.valueOf(DatabaseManager.getBugunkuRandevu()), "", new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Poliklinik",
                String.valueOf(DatabaseManager.getToplamPoliklinik()), "", new Color(241, 196, 15)));
        statsPanel.add(createStatCard("Reçete",
                String.valueOf(DatabaseManager.getToplamRecete()), "", new Color(230, 126, 34)));
        statsPanel.add(createStatCard("Aktif Randevular",
                String.valueOf(DatabaseManager.getAktifRandevular()), "", new Color(22, 160, 133)));

        panel.add(statsPanel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 240, 240), 1),
                BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        iconLabel.setForeground(color);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_COLOR);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(TEXT_COLOR);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(153, 153, 153));

        contentPanel.add(valueLabel, BorderLayout.CENTER);
        contentPanel.add(titleLabel, BorderLayout.SOUTH);

        card.add(iconLabel, BorderLayout.WEST);
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    // DOKTOR PANELİ - CRUD OPERASYONLARI
    private JPanel createDoctorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel(" Doktor Yönetimi");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_COLOR);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = new JButton("+ Yeni Doktor");
        JButton editButton = new JButton("️ Düzenle");
        JButton deleteButton = new JButton(" X Sil");
        JButton refreshButton = new JButton(" Yenile");

        stylePrimaryButton(addButton);
        styleSecondaryButton(editButton);
        styleDangerButton(deleteButton);
        styleSecondaryButton(refreshButton);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        header.add(title, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);

        // Tablo
        JTable table = new JTable(doktorTableModel);
        styleModernTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Buton aksiyonları
        addButton.addActionListener(e -> showDoktorEkleForm());
        editButton.addActionListener(e -> showDoktorDuzenleForm(table));
        deleteButton.addActionListener(e -> silDoktor(table));
        refreshButton.addActionListener(e -> loadDoktorlar());

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadDoktorlar();
        return panel;
    }

    private void showDoktorEkleForm() {
        JDialog dialog = new JDialog(this, "Yeni Doktor Ekle", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form alanları
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Doktor No:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField txtDoktorNo = new JTextField();
        formPanel.add(txtDoktorNo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("TC Kimlik:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JTextField txtTcKimlik = new JTextField();
        formPanel.add(txtTcKimlik, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Ad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField txtAd = new JTextField();
        formPanel.add(txtAd, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Soyad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JTextField txtSoyad = new JTextField();
        formPanel.add(txtSoyad, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Uzmanlık:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JTextField txtUzmanlik = new JTextField();
        formPanel.add(txtUzmanlik, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Poliklinik:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        JComboBox<String> cmbPoliklinik = new JComboBox<>();
        List<Object[]> poliklinikler = DatabaseManager.getPolikliniklerForCombo();
        for (Object[] pol : poliklinikler) {
            cmbPoliklinik.addItem(pol[1].toString());
        }
        formPanel.add(cmbPoliklinik, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        JTextField txtTelefon = new JTextField();
        formPanel.add(txtTelefon, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("E-posta:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        JTextField txtEmail = new JTextField();
        formPanel.add(txtEmail, gbc);

        // Butonlar
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton btnKaydet = new JButton("💾 Kaydet");
        JButton btnIptal = new JButton("❌ İptal");

        stylePrimaryButton(btnKaydet);
        styleSecondaryButton(btnIptal);

        buttonPanel.add(btnKaydet);
        buttonPanel.add(btnIptal);
        formPanel.add(buttonPanel, gbc);

        btnKaydet.addActionListener(e -> {
            if (validateDoktorForm(txtDoktorNo, txtTcKimlik, txtAd, txtSoyad)) {
                int poliklinikId = poliklinikler.get(cmbPoliklinik.getSelectedIndex())[0] instanceof Integer ?
                        (Integer) poliklinikler.get(cmbPoliklinik.getSelectedIndex())[0] : 1;

                boolean success = DatabaseManager.doktorEkle(
                        txtDoktorNo.getText().trim(),
                        txtTcKimlik.getText().trim(),
                        txtAd.getText().trim(),
                        txtSoyad.getText().trim(),
                        txtUzmanlik.getText().trim(),
                        poliklinikId,
                        "Uzm. Dr.",
                        txtTelefon.getText().trim(),
                        txtEmail.getText().trim()
                );

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Doktor başarıyla eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    loadDoktorlar();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Doktor eklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIptal.addActionListener(e -> dialog.dispose());

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private boolean validateDoktorForm(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                field.requestFocus();
                return false;
            }
        }
        return true;
    }

    private void showDoktorDuzenleForm(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen düzenlemek için bir doktor seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int doktorId = (int) table.getValueAt(selectedRow, 0);
        // Düzenleme formunu göster (ekleme formuna benzer)
        JOptionPane.showMessageDialog(this, "Düzenleme özelliği yakında eklenecek!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void silDoktor(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir doktor seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int doktorId = (int) table.getValueAt(selectedRow, 0);
        String doktorAdi = table.getValueAt(selectedRow, 2) + " " + table.getValueAt(selectedRow, 3);

        int confirm = JOptionPane.showConfirmDialog(this,
                doktorAdi + " isimli doktoru silmek istediğinize emin misiniz?",
                "Doktor Silme", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = DatabaseManager.doktorSil(doktorId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Doktor başarıyla silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                loadDoktorlar();
            } else {
                JOptionPane.showMessageDialog(this, "Doktor silinirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // DİĞER PANELLER
    private JPanel createPatientsPanel() {
        return createModernTablePanel(" Hasta Yönetimi", hastaTableModel, "hasta");
    }

    private JPanel createAppointmentsPanel() {
        return createModernTablePanel(" Randevu Yönetimi", randevuTableModel, "randevu");
    }

    private JPanel createClinicsPanel() {
        return createModernTablePanel(" Poliklinik Yönetimi", poliklinikTableModel, "poliklinik");
    }

    private JPanel createPrescriptionsPanel() {
        return createModernTablePanel("  Reçete Yönetimi", receteTableModel, "recete");
    }

    private JPanel createModernTablePanel(String title, DefaultTableModel model, String type) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = new JButton("+ Yeni Ekle");
        JButton deleteButton = new JButton(" X️ Sil");
        JButton printButton = new JButton(" Yazdır");
        JButton refreshButton = new JButton(" Yenile");

        stylePrimaryButton(addButton);
        styleDangerButton(deleteButton);
        styleSecondaryButton(printButton);
        styleSecondaryButton(refreshButton);

        // Tablo oluştur
        JTable table = new JTable(model);
        styleModernTable(table);

        // Tabloyu uygun değişkene ata
        switch (type) {
            case "doktor":
                doktorTable = table;
                break;
            case "hasta":
                hastaTable = table;
                break;
            case "randevu":
                randevuTable = table;
                break;
            case "poliklinik":
                poliklinikTable = table;
                break;
            case "recete":
                receteTable = table;
                break;
        }

        // Buton aksiyonları - doğrudan metodları çağır
        addButton.addActionListener(e -> {
            switch (type) {
                case "hasta":
                    showHastaEkleForm();
                    break;
                case "randevu":
                    showRandevuEkleForm();
                    break;
                case "poliklinik":
                    showPoliklinikEkleForm();
                    break;
                case "recete":
                    showReceteEkleForm();
                    break;
            }
        });

        deleteButton.addActionListener(e -> {
            switch (type) {
                case "hasta":
                    silHasta();
                    break;
                case "randevu":
                    silRandevu();
                    break;
                case "poliklinik":
                    silPoliklinik();
                    break;
                case "recete":
                    silRecete();
                    break;
            }
        });

        printButton.addActionListener(e -> {
            PrintUtility.printTable(table, title);
        });

        refreshButton.addActionListener(e -> refreshPanelData(type));

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton);
        buttonPanel.add(refreshButton);

        header.add(titleLabel, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);

        // Arama paneli
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(BACKGROUND_COLOR);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        SearchPanel searchUtil = new SearchPanel(table);
        searchPanel.add(searchUtil, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        panel.add(header, BorderLayout.NORTH);
        panel.add(searchPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        refreshPanelData(type);

        return panel;
    }

    private void silRecete() {
    }

    private void silPoliklinik() {
        JTable table = new JTable();
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir poliklinik seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int poliklinikId = (int) table.getValueAt(selectedRow, 0);
        String poliklinikAdi = table.getValueAt(selectedRow, 1).toString();
        int doktorSayisi = (int) table.getValueAt(selectedRow, 5);

        if (doktorSayisi > 0) {
            JOptionPane.showMessageDialog(this,
                    "Bu poliklinikte " + doktorSayisi + " doktor bulunuyor!\nÖnce doktorları başka polikliniklere taşıyın.",
                    "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                poliklinikAdi + " polikliniğini silmek istediğinize emin misiniz?",
                "Poliklinik Silme", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = DatabaseManager.poliklinikSil(poliklinikId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Poliklinik başarıyla silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                loadPoliklinikler();
            } else {
                JOptionPane.showMessageDialog(this, "Poliklinik silinirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void silRandevu() {
        if (randevuTable == null) {
            JOptionPane.showMessageDialog(this, "Randevu tablosu yüklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = randevuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir randevu seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = randevuTable.convertRowIndexToModel(selectedRow);

        int randevuId = (int) randevuTableModel.getValueAt(modelRow, 0);
        String hastaAdi = randevuTableModel.getValueAt(modelRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                hastaAdi + " için olan randevuyu silmek istediğinize emin misiniz?",
                "Randevu Silme", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = DatabaseManager.randevuSil(randevuId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Randevu başarıyla silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                loadRandevular();
            } else {
                JOptionPane.showMessageDialog(this, "Randevu silinirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void silHasta() {
        if (hastaTable == null) {
            JOptionPane.showMessageDialog(this, "Hasta tablosu yüklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = hastaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir hasta seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Seçili satırı model indeksine çevir (filtreleme varsa)
        int modelRow = hastaTable.convertRowIndexToModel(selectedRow);

        int hastaId = (int) hastaTableModel.getValueAt(modelRow, 0);
        String hastaAdi = hastaTableModel.getValueAt(modelRow, 2) + " " + hastaTableModel.getValueAt(modelRow, 3);

        int confirm = JOptionPane.showConfirmDialog(this,
                hastaAdi + " isimli hastayı silmek istediğinize emin misiniz?\nBu işlem geri alınamaz!",
                "Hasta Silme", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = DatabaseManager.hastaSil(hastaId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Hasta başarıyla silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                loadHastalar();
            } else {
                JOptionPane.showMessageDialog(this, "Hasta silinirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // DİĞER EKLEME FORMLARI (kısaltılmış)
    private void showHastaEkleForm() {
        JDialog dialog = new JDialog(this, "Yeni Hasta Ekle", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form alanları
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("TC Kimlik:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField txtTcKimlik = new JTextField();
        formPanel.add(txtTcKimlik, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Ad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JTextField txtAd = new JTextField();
        formPanel.add(txtAd, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Soyad:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField txtSoyad = new JTextField();
        formPanel.add(txtSoyad, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Doğum Tarihi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JTextField txtDogumTarihi = new JTextField();
        txtDogumTarihi.putClientProperty("JTextField.placeholderText", "GG.AA.YYYY");
        formPanel.add(txtDogumTarihi, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Cinsiyet:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JComboBox<String> cmbCinsiyet = new JComboBox<>(new String[]{"Erkek", "Kadın"});
        formPanel.add(cmbCinsiyet, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        JTextField txtTelefon = new JTextField();
        formPanel.add(txtTelefon, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("E-posta:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        JTextField txtEmail = new JTextField();
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Adres:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        JTextArea txtAdres = new JTextArea(3, 20);
        txtAdres.setLineWrap(true);
        JScrollPane scrollAdres = new JScrollPane(txtAdres);
        formPanel.add(scrollAdres, gbc);

        // Butonlar
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton btnKaydet = new JButton("💾 Kaydet");
        JButton btnIptal = new JButton("❌ İptal");

        stylePrimaryButton(btnKaydet);
        styleSecondaryButton(btnIptal);

        buttonPanel.add(btnKaydet);
        buttonPanel.add(btnIptal);
        formPanel.add(buttonPanel, gbc);

        btnKaydet.addActionListener(e -> {
            if (validateHastaForm(txtTcKimlik, txtAd, txtSoyad, txtTelefon)) {
                try {
                    // Tarih dönüşümü
                    Date dogumTarihi = null;
                    if (!txtDogumTarihi.getText().trim().isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        java.util.Date utilDate = sdf.parse(txtDogumTarihi.getText().trim());
                        dogumTarihi = new Date(utilDate.getTime());
                    }

                    boolean success = DatabaseManager.hastaEkle(
                            txtTcKimlik.getText().trim(),
                            txtAd.getText().trim(),
                            txtSoyad.getText().trim(),
                            dogumTarihi,
                            cmbCinsiyet.getSelectedItem().toString(),
                            txtTelefon.getText().trim(),
                            txtEmail.getText().trim(),
                            txtAdres.getText().trim()
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Hasta başarıyla eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        loadHastalar();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Hasta eklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Tarih formatı hatalı! (GG.AA.YYYY)", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIptal.addActionListener(e -> dialog.dispose());

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private boolean validateHastaForm(JTextField tcKimlik, JTextField ad, JTextField soyad, JTextField telefon) {
        if (tcKimlik.getText().trim().length() != 11) {
            JOptionPane.showMessageDialog(this, "TC Kimlik 11 haneli olmalıdır!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            tcKimlik.requestFocus();
            return false;
        }

        if (ad.getText().trim().isEmpty() || soyad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ad ve soyad alanları zorunludur!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            ad.requestFocus();
            return false;
        }

        return true;
    }

    private void showRandevuEkleForm() {
        JDialog dialog = new JDialog(this, "Yeni Randevu Ekle", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Hasta seçimi
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Hasta:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JComboBox<String> cmbHasta = new JComboBox<>();
        List<Object[]> hastalar = DatabaseManager.getHastalarForCombo();
        for (Object[] hasta : hastalar) {
            cmbHasta.addItem(hasta[1].toString());
        }
        formPanel.add(cmbHasta, gbc);

        // Doktor seçimi
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Doktor:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JComboBox<String> cmbDoktor = new JComboBox<>();
        List<Object[]> doktorlar = DatabaseManager.getDoktorlarForCombo();
        for (Object[] doktor : doktorlar) {
            cmbDoktor.addItem(doktor[1].toString());
        }
        formPanel.add(cmbDoktor, gbc);

        // Poliklinik seçimi
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Poliklinik:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JComboBox<String> cmbPoliklinik = new JComboBox<>();
        List<Object[]> poliklinikler = DatabaseManager.getPolikliniklerForCombo();
        for (Object[] pol : poliklinikler) {
            cmbPoliklinik.addItem(pol[1].toString());
        }
        formPanel.add(cmbPoliklinik, gbc);

        // Tarih ve saat
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Tarih:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JTextField txtTarih = new JTextField();
        txtTarih.putClientProperty("JTextField.placeholderText", "GG.AA.YYYY");
        formPanel.add(txtTarih, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Saat:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JComboBox<String> cmbSaat = new JComboBox<>(new String[]{
                "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
                "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00"
        });
        formPanel.add(cmbSaat, gbc);

        // Açıklama
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Açıklama:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        JTextArea txtAciklama = new JTextArea(3, 20);
        txtAciklama.setLineWrap(true);
        JScrollPane scrollAciklama = new JScrollPane(txtAciklama);
        formPanel.add(scrollAciklama, gbc);

        // Butonlar
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton btnKaydet = new JButton("💾 Randevu Oluştur");
        JButton btnIptal = new JButton("❌ İptal");

        stylePrimaryButton(btnKaydet);
        styleSecondaryButton(btnIptal);

        buttonPanel.add(btnKaydet);
        buttonPanel.add(btnIptal);
        formPanel.add(buttonPanel, gbc);

        btnKaydet.addActionListener(e -> {
            if (validateRandevuForm(cmbHasta, cmbDoktor, cmbPoliklinik, txtTarih)) {
                try {
                    int hastaId = (Integer) hastalar.get(cmbHasta.getSelectedIndex())[0];
                    int doktorId = (Integer) doktorlar.get(cmbDoktor.getSelectedIndex())[0];
                    int poliklinikId = (Integer) poliklinikler.get(cmbPoliklinik.getSelectedIndex())[0];

                    // Tarih ve saat birleştirme
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    String tarihSaat = txtTarih.getText().trim() + " " + cmbSaat.getSelectedItem();
                    java.util.Date utilDate = sdf.parse(tarihSaat);
                    Timestamp randevuTarihi = new Timestamp(utilDate.getTime());

                    boolean success = DatabaseManager.randevuEkle(
                            hastaId, doktorId, poliklinikId, randevuTarihi, txtAciklama.getText().trim()
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Randevu başarıyla oluşturuldu!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        loadRandevular();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Randevu oluşturulurken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Tarih formatı hatalı! (GG.AA.YYYY)", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIptal.addActionListener(e -> dialog.dispose());

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private boolean validateRandevuForm(JComboBox<String> cmbHasta, JComboBox<String> cmbDoktor, JComboBox<String> cmbPoliklinik, JTextField txtTarih) {
        return false;
    }

    private void showPoliklinikEkleForm() {
        JDialog dialog = new JDialog(this, "Yeni Poliklinik Ekle", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form alanları
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Poliklinik Adı:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField txtAd = new JTextField();
        formPanel.add(txtAd, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Açıklama:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JTextArea txtAciklama = new JTextArea(3, 20);
        txtAciklama.setLineWrap(true);
        JScrollPane scrollAciklama = new JScrollPane(txtAciklama);
        formPanel.add(scrollAciklama, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Kat:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JComboBox<String> cmbKat = new JComboBox<>(new String[]{"Zemin Kat", "1. Kat", "2. Kat", "3. Kat", "4. Kat", "5. Kat"});
        formPanel.add(cmbKat, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JTextField txtTelefon = new JTextField();
        formPanel.add(txtTelefon, gbc);

        // Butonlar
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton btnKaydet = new JButton("💾 Kaydet");
        JButton btnIptal = new JButton("❌ İptal");

        stylePrimaryButton(btnKaydet);
        styleSecondaryButton(btnIptal);

        buttonPanel.add(btnKaydet);
        buttonPanel.add(btnIptal);
        formPanel.add(buttonPanel, gbc);

        btnKaydet.addActionListener(e -> {
            if (validatePoliklinikForm(txtAd)) {
                boolean success = DatabaseManager.poliklinikEkle(
                        txtAd.getText().trim(),
                        txtAciklama.getText().trim(),
                        cmbKat.getSelectedItem().toString(),
                        txtTelefon.getText().trim()
                );

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Poliklinik başarıyla eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    loadPoliklinikler();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Poliklinik eklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIptal.addActionListener(e -> dialog.dispose());

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private boolean validatePoliklinikForm(JTextField ad) {
        if (ad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Poliklinik adı zorunludur!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            ad.requestFocus();
            return false;
        }
        return true;
    }

    private void showReceteEkleForm() {
        JDialog dialog = new JDialog(this, "Yeni Reçete Oluştur", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Hasta seçimi
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Hasta:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JComboBox<String> cmbHasta = new JComboBox<>();
        List<Object[]> hastalar = DatabaseManager.getHastalarForCombo();
        for (Object[] hasta : hastalar) {
            cmbHasta.addItem(hasta[1].toString());
        }
        formPanel.add(cmbHasta, gbc);

        // Doktor seçimi
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Doktor:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JComboBox<String> cmbDoktor = new JComboBox<>();
        List<Object[]> doktorlar = DatabaseManager.getDoktorlarForCombo();
        for (Object[] doktor : doktorlar) {
            cmbDoktor.addItem(doktor[1].toString());
        }
        formPanel.add(cmbDoktor, gbc);

        // İlaç bilgileri
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("İlaç Adı:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField txtIlacAdi = new JTextField();
        formPanel.add(txtIlacAdi, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Dozaj:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JTextField txtDozaj = new JTextField();
        formPanel.add(txtDozaj, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Kullanım Şekli:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JComboBox<String> cmbKullanim = new JComboBox<>(new String[]{
                "Ağızdan", "Damlalık", "Enjeksiyon", "Krem", "Merhem", "Sprey", "Şurup", "Tablet"
        });
        formPanel.add(cmbKullanim, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Kullanım Sıklığı:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        JComboBox<String> cmbSiklik = new JComboBox<>(new String[]{
                "Günde 1 kez", "Günde 2 kez", "Günde 3 kez", "Günde 4 kez",
                "Haftada 1 kez", "Ayda 1 kez", "İhtiyaç halinde"
        });
        formPanel.add(cmbSiklik, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Süre:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        JComboBox<String> cmbSure = new JComboBox<>(new String[]{
                "3 gün", "5 gün", "7 gün", "10 gün", "14 gün", "21 gün", "30 gün", "Süresiz"
        });
        formPanel.add(cmbSure, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Notlar:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        JTextArea txtNotlar = new JTextArea(3, 20);
        txtNotlar.setLineWrap(true);
        JScrollPane scrollNotlar = new JScrollPane(txtNotlar);
        formPanel.add(scrollNotlar, gbc);

        // Butonlar
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton btnKaydet = new JButton("💾 Reçete Oluştur");
        JButton btnIptal = new JButton("❌ İptal");

        stylePrimaryButton(btnKaydet);
        styleSecondaryButton(btnIptal);

        buttonPanel.add(btnKaydet);
        buttonPanel.add(btnIptal);
        formPanel.add(buttonPanel, gbc);

        btnKaydet.addActionListener(e -> {
            if (validateReceteForm(cmbHasta, cmbDoktor, txtIlacAdi)) {
                int hastaId = (Integer) hastalar.get(cmbHasta.getSelectedIndex())[0];
                int doktorId = (Integer) doktorlar.get(cmbDoktor.getSelectedIndex())[0];

                boolean success = DatabaseManager.receteEkle(
                        hastaId,
                        doktorId,
                        txtIlacAdi.getText().trim(),
                        txtDozaj.getText().trim(),
                        cmbKullanim.getSelectedItem().toString(),
                        cmbSiklik.getSelectedItem().toString(),
                        cmbSure.getSelectedItem().toString(),
                        txtNotlar.getText().trim()
                );

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Reçete başarıyla oluşturuldu!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    loadReceteler();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Reçete oluşturulurken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnIptal.addActionListener(e -> dialog.dispose());

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private boolean validateReceteForm(JComboBox<String> hasta, JComboBox<String> doktor, JTextField ilacAdi) {
        if (hasta.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir hasta seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (doktor.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir doktor seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (ilacAdi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "İlaç adı zorunludur!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            ilacAdi.requestFocus();
            return false;
        }
        return true;
    }

    // STYLING METODLARI
    private void styleModernTable(JTable table) {
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(236, 240, 241));
    }

    private void stylePrimaryButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(108, 117, 125));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleDangerButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(220, 53, 69));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Main().setVisible(true);
        });
    }
}