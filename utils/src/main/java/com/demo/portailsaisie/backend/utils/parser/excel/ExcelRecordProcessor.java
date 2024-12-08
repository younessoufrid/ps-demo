package com.demo.portailsaisie.backend.utils.parser.excel;

import com.demo.portailsaisie.backend.utils.parser.Record;
import com.demo.portailsaisie.backend.utils.parser.RecordProcessor;
import com.demo.portailsaisie.backend.utils.parser.excel.annotation.ExcelBindByName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ExcelRecordProcessor implements RecordProcessor {
    @Override
    public <INPUT extends Record, OUTPUT> List<OUTPUT> processRecord(INPUT record, Class<OUTPUT> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        OUTPUT instance = instantiateBean(clazz);
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelBindByName.class)) {
                ExcelBindByName fieldAnnotation = field.getAnnotation(ExcelBindByName.class);
                field.setAccessible(true);
                boolean isRequired = fieldAnnotation.required();
                // check field type
                try {
                    switch (field.getType().getTypeName()) {
                        case "java.lang.Integer":
                        case "int":
                            field.set(instance, record.getInt(fieldAnnotation.column(), isRequired));
                            break;
                        case "java.lang.String":
                            field.set(instance, record.getString(fieldAnnotation.column(), isRequired));
                            break;
                        case "java.lang.Double":
                        case "double":
                            field.set(instance, record.getDouble(fieldAnnotation.column(), isRequired));
                            break;
                        case "java.lang.Long":
                        case "long":
                            field.set(instance, record.getLong(fieldAnnotation.column(), isRequired));
                            break;
                        case "java.util.Date":
                            field.set(instance, record.getDate(fieldAnnotation.column(), isRequired));
                            break;
                        default:
                            throw new UnsupportedOperationException("Unsupported field type: " + field.getType().getName());
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Error setting field value", e);
                } finally {
                    field.setAccessible(false);
                }
            }
        }
        return List.of(instance);
    }

    private static <T> T instantiateBean(Class<T> clazz) {
        T instance;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(String.format("Failed to instantiate bean of type %s: %s", clazz.getName(), e.getMessage()), e);
        }
        return instance;
    }

}
