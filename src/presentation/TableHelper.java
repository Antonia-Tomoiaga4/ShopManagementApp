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


            int start = 0;
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getName().equalsIgnoreCase("id") || fields[i].getName().toLowerCase().contains("id")) {
                    columnNames[0] = fields[i].getName();
                    Field temp = fields[i];
                    fields[i] = fields[0];
                    fields[0] = temp;
                    start = 1;
                    break;
                }
            }

            for (int i = start; i < fields.length; i++) {
                if (columnNames[i] == null) {
                    columnNames[i] = fields[i].getName();
                }
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
