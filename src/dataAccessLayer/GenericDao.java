package dataAccessLayer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *metode generice findAll findbyId insert update si delete pentru mai multe tipuri de obiecte
 * folosita pt a nu scrie cod duplicat pt fiecare tip de obiect
 * @param <T>
 */
public class GenericDao<T> {

    private final Class<T> type;

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public List<T> findAll() {
        List<T> result = new ArrayList<>();

        String tableName = "`" + type.getSimpleName() + "`";
        String query = "SELECT * FROM " + tableName;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                //new instance creaza un obiect dinamic
                T obj = type.getDeclaredConstructor().newInstance();

                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = rs.getObject(field.getName());
                    if (value != null) {
                        if (field.getType() == int.class && value instanceof Number) {
                            field.setInt(obj, ((Number) value).intValue());
                        } else if (field.getType() == double.class && value instanceof Number) {
                            field.setDouble(obj, ((Number) value).doubleValue());
                        } else {
                            field.set(obj, value);
                        }
                    }
                }
                result.add(obj);
            }

        } catch (SQLException | InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean insert(T obj) {
        String tableName = "`" + type.getSimpleName() + "`";
        Field[] fields = type.getDeclaredFields();

        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                columns.append(field.getName()).append(",");
                placeholders.append("?,");

            }
        }

        columns.deleteCharAt(columns.length() - 1);
        placeholders.deleteCharAt(placeholders.length() - 1);

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int index = 1;
            for (Field field : fields) {
                if (!field.getName().equalsIgnoreCase("id")) {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    stmt.setObject(index++, value);
                }
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Field idField = type.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.setInt(obj, generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String tableName = "`" + type.getSimpleName() + "`";
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(T obj) {
        String tableName = "`" + type.getSimpleName() + "`";
        Field[] fields = type.getDeclaredFields();

        StringBuilder setClause = new StringBuilder();

        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                setClause.append(field.getName()).append(" = ?,");
            }
        }
        setClause.deleteCharAt(setClause.length() - 1);

        String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 1;
            for (Field field : fields) {
                if (!field.getName().equalsIgnoreCase("id")) {
                    field.setAccessible(true);
                    stmt.setObject(index++, field.get(obj));
                }
            }

            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            stmt.setObject(index, idField.get(obj));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }

    public T findById(int id) {
        String tableName = "`" + type.getSimpleName() + "`";
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    T obj = type.getDeclaredConstructor().newInstance();

                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        Object value = rs.getObject(field.getName());
                        if (value != null) {
                            if (field.getType() == int.class && value instanceof Number) {
                                field.setInt(obj, ((Number) value).intValue());
                            } else if (field.getType() == double.class && value instanceof Number) {
                                field.setDouble(obj, ((Number) value).doubleValue());
                            } else {
                                field.set(obj, value);
                            }
                        }
                    }
                    return obj;
                }
            }

        } catch (SQLException | InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
