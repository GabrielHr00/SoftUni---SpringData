package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EntityManager<E> implements DBContext<E> {
    private Connection connection;

    public EntityManager(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getIdColumn(entity.getClass());
        primaryKey.setAccessible(true);
        Object value = primaryKey.get(entity);

        if(value == null || (long) value <= 0){
            return doInsert(entity, primaryKey);
        }

        return doUpdate(entity, primaryKey);
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return Arrays.stream(table.getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(Entity.class))
                .map(e -> e.getClass())
                .collect(Collectors.toMap(e -> e));
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String tableName = getTableName(table);

        String query = String.format("SELECT * FROM %s LIMIT 1;", tableName);
        ResultSet rs = statement.executeQuery(query);
        E entity = table.getDeclaredConstructor().newInstance();

        rs.next();
        fillEntity(table, rs, entity);

        return entity;
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String tableName = getTableName(table);

        String query = String.format("SELECT * FROM %s %s LIMIT 1; ", tableName, where !=  null ? "WHERE " + where : "");
        ResultSet rs = statement.executeQuery(query);
        E entity = table.getDeclaredConstructor().newInstance();

        rs.next();
        fillEntity(table, rs, entity);

        return entity;
    }

    private void fillEntity(Class<E> table, ResultSet rs, E entity) throws SQLException, IllegalAccessException {
        Field[] declaredFields = Arrays.stream(table.getDeclaredFields()).toArray(Field[]::new);

        for (Field f : declaredFields){
            f.setAccessible(true);
            fillField(f, rs, entity);
        }
    }

    private void fillField(Field f, ResultSet rs, E entity) throws SQLException, IllegalAccessException {
        f.setAccessible(true);

        if(f.getType() == int.class || f.getType() == long.class){
            f.set(entity, rs.getInt(f.getName()));
        } else if(f.getType() == LocalDate.class){
            f.set(entity, LocalDate.parse(rs.getString(f.getName())));
        }else{
            f.set(entity, rs.getString(f.getName()));
        }
    }

    private Field getIdColumn(Class<?> clazz){
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity missing an Id column"));
    }

    private boolean doUpdate(E entity, Field primaryKey) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());
        String tableField = getColumnsWithoutId(entity.getClass());
        String valuesToUpdate = getColumnsValuesWithoutId(entity);
        String idValue = getIdValue(entity);

        String updateQuery = String.format("UPDATE %s SET (%s) = (%s) WHERE id = %d", tableName, tableField, valuesToUpdate, idValue);
        return connection.prepareStatement(updateQuery).execute();
    }

    private String getIdValue(E entity) throws IllegalAccessException {
         List<Field> ids = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(Id.class)).collect(Collectors.toList());
         String id = "";
         for (Field f: ids) {
            f.setAccessible(true);
            Object o = f.get(entity);

            if(o != null && !o.toString().equals("")){
                id = o.toString();
            }
         }
         return id;
    }

    private boolean doInsert(E entity, Field primaryKey) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        String tableFields = getColumnsWithoutId(entity.getClass());
        String tableValues = getColumnsValuesWithoutId(entity);

        String insertQuery = String.format("INSERT INTO %s (%s) VALUES(%s)", tableName, tableFields, tableValues);
        return connection.prepareStatement(insertQuery).execute();
    }

    private String getColumnsValuesWithoutId(E entity) throws IllegalAccessException {
        Class<?> aClass = entity.getClass();
        List<Field> fields  = Arrays.stream(aClass.getDeclaredFields())
                .filter(e -> !e.isAnnotationPresent(Id.class))
                .filter(e -> e.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        List<String> values = new ArrayList<>();

        for (var f: fields) {
            f.setAccessible(true);
            Object o = f.get(entity);

            if(o instanceof String || o instanceof LocalDate){
                values.add("'" + o + "'");
            }
            else{
                values.add(o.toString());
            }
        }
        return String.join(",", values);
    }

    private String getColumnsWithoutId(Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(e -> !e.isAnnotationPresent(Id.class))
                .filter(e -> e.isAnnotationPresent(Column.class))
                .map(e -> e.getAnnotationsByType(Column.class))
                .map(a -> a[0].name())
                .collect(Collectors.joining(","));
    }

    private String getTableName(Class<?> aClass) {
        Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);
        if(annotationsByType.length == 0){
            throw new UnsupportedOperationException("Class must be entity");
        }
        return annotationsByType[0].name();
    }
}
