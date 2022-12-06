package javapart.data.types;

import javapart.data.types.Comparator;

import java.io.InputStreamReader;

public interface UserType {
    public String typeName(); // Имя типа
    public Object create(); // Создает объект
    public Object clone(); // Клонирует текущий
    public Object readValue(InputStreamReader in); // Создает и читает объект
    public Object parseValue(String ss); // Создает и парсит содержимое из строки
    public Comparator <Object> getTypeComparator(); // Возвращает компаратор для сравнения
}
