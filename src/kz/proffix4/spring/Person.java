package kz.proffix4.spring;

/**
 * Класс, соответсвующий записи в таблице Person
 *
 */
public class Person {

    int id;            // Код записи
    String firstName;  // Имя клиента
    String lastName;   // Фамилия клиента
    int age;           // Возраст клиента

    public Person() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.age = 0;
    }

    public Person(String firstName, String lastName, int age) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Имя=%s, Фаилия=%s, Возраст=%d", firstName, lastName, age);
    }

}
