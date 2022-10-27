package kz.proffix4.spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.TransactionStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import java.util.List;

/**
 * Реализация интерфейса работы с таблицей Person
 *
 */
public class PersonDAO implements IPersonDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Person person) { // Реализация вставки новой записи
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        jt.update("INSERT INTO PERSON (FIRSTNAME, LASTNAME, AGE) VALUES(?,?,?)",
                new Object[]{person.getFirstName(), person.getLastName(), person.getAge()});
    }

    @Override
    public void append(String firstName, String lastName, int age) {  // Реализация добавления новой записи
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        jt.update("INSERT INTO PERSON (FIRSTNAME, LASTNAME, AGE) VALUES(?,?,?)",
                new Object[]{firstName, lastName, age});
    }

    @Override
    public void deleteByLastName(String lastname) {  // Реализация удаления записей по фамилии
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        jt.update("DELETE FROM PERSON WHERE LASTNAME LIKE ?", new Object[]{'%' + lastname + '%'});
    }

    @Override
    public void delete(final String firstName, final String lastName) {  // Реализация удаления записей с указанным именем и фамилией
        TransactionTemplate tt = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        tt.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                try {
                    JdbcTemplate jt = new JdbcTemplate(dataSource);
                    jt.update("DELETE from PERSON where FIRSTNAME= ? AND LASTNAME = ?", new Object[]{firstName, lastName});
                } catch (RuntimeException e) {
                    status.setRollbackOnly();
                    throw e;
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    @Override
    public void deleteAll() {  // Реализация удаления всех запией
        try {
            JdbcTemplate jt = new JdbcTemplate(dataSource);
            jt.update("DELETE from PERSON");
        } catch (Exception e) {
        }
    }

    @Override
    public void update(String oldLastName, String newLastName) {  // Изменение записей в таблице
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        jt.update("UPDATE PERSON SET LASTNAME = ? WHERE LASTNAME = ?", new Object[]{newLastName, oldLastName});
    }

    @Override
    public Person findByAge(int age) { // Реализация роиска записи с заданным возрастом
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        List<Person> person = jt.query("SELECT * FROM PERSON WHERE AGE = ?",
                new Object[]{age}, new PersonRowMapper());
        return person.size() > 0 ? person.get(0) : null;
    }

    @Override
    public List<Person> findByFirstName(String lastname) {  // Реализация поиска записей по имени
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM PERSON WHERE FIRSTNAME LIKE ?";
        List<Person> persons = jt.query(sql, new Object[]{'%' + lastname + '%'}, new PersonRowMapper());
        return persons;
    }

    @Override
    public List<Person> select(String firstname, String lastname) {  // Реализация получения записей с заданным именем и фамилией
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        return jt.query("select  * from PERSON where FIRSTNAME = ? AND LASTNAME= ?",
                new Object[]{firstname, lastname}, new PersonRowMapper());
    }

    @Override
    public List<Person> selectAll() {  // Реализация получения всех записей
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        return jt.query("select * from PERSON", new PersonRowMapper());
    }

}
