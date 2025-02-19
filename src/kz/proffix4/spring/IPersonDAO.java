package kz.proffix4.spring;

import javax.sql.DataSource;
import java.util.List;

/**
 * Интерфейс работы с таблицей Person
 *
 */
public interface IPersonDAO {
    void setDataSource(DataSource ds); // Установка связи с данныими
    void insert(Person person); // Вставка новой записи
    void append(String firstName, String lastName, int age); // Добавление новой записи
    void deleteByLastName(String lastName); // Удаление записи по фамилии
    void delete(String firstName, String lastName); // Удаление записи с указанным именем и фамилией
    void deleteAll(); // Удаление всех запией
    void update(String oldLastName, String newLastName); // Изменение записей в таблице
    Person findByAge(int id); // Получение записи с заданным возрастом
    List<Person> findByFirstName(String firstName); // Получение записей с заданным именем 
    List<Person> select(String firstName, String lastName); // Получение записей с заданным именем и фамилией
    List<Person> selectAll(); // Получение всех записей
}
