package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class TableHelper {
    public static <T> void populateTable(JTable table, List<T> list, Class<T> clazz) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            String[] columnNames = new String[fields.length];

            for (int i = 0; i < fields.length; i++) {
                columnNames[i] = fields[i].getName();
            }

            Object[][] data = new Object[list.size()][fields.length];

            for (int i = 0; i < list.size(); i++) {
                T obj = list.get(i);
                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    data[i][j] = fields[j].get(obj);
                }
            }

            table.setModel(new DefaultTableModel(data, columnNames));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
