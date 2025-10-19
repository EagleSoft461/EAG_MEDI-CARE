package util;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.print.*;

public class PrintUtility {

    public static void printTable(JTable table, String title) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(title);

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Başlık
                Font titleFont = new Font("Arial", Font.BOLD, 16);
                g2d.setFont(titleFont);
                g2d.drawString(title, 50, 50);

                // Tablo
                int y = 80;

                // Tablo başlıkları
                JTableHeader header = table.getTableHeader();
                int columnCount = table.getColumnCount();
                int[] columnWidths = new int[columnCount];

                Font headerFont = new Font("Arial", Font.BOLD, 12);
                g2d.setFont(headerFont);

                // Başlık satırı
                for (int i = 0; i < columnCount; i++) {
                    String columnName = table.getColumnName(i);
                    columnWidths[i] = Math.max(80, columnName.length() * 7);
                    g2d.drawString(columnName, getXPosition(i, columnWidths), y);
                }

                y += 20;

                // Veri satırları
                Font dataFont = new Font("Arial", Font.PLAIN, 10);
                g2d.setFont(dataFont);

                int rowCount = Math.min(table.getRowCount(), 50); // Maksimum 50 satır
                for (int row = 0; row < rowCount; row++) {
                    for (int col = 0; col < columnCount; col++) {
                        Object value = table.getValueAt(row, col);
                        if (value != null) {
                            String text = value.toString();
                            // Metni kısalt
                            if (text.length() > 20) {
                                text = text.substring(0, 17) + "...";
                            }
                            g2d.drawString(text, getXPosition(col, columnWidths), y);
                        }
                    }
                    y += 15;

                    // Sayfa sınırı kontrolü
                    if (y > pageFormat.getImageableHeight() - 50) {
                        break;
                    }
                }

                // Alt bilgi
                g2d.setFont(new Font("Arial", Font.ITALIC, 10));
                g2d.drawString("Toplam Kayıt: " + table.getRowCount(), 50, y + 30);
                g2d.drawString("Yazdırma Tarihi: " + new java.util.Date(), 50, y + 45);

                return PAGE_EXISTS;
            }

            private int getXPosition(int columnIndex, int[] columnWidths) {
                int x = 50;
                for (int i = 0; i < columnIndex; i++) {
                    x += columnWidths[i] + 10;
                }
                return x;
            }
        });

        try {
            if (job.printDialog()) {
                job.print();
                JOptionPane.showMessageDialog(null, "Yazdırma işlemi başlatıldı!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Yazdırma hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}