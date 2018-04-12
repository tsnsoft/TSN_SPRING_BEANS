package kz.proffix4.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import java.util.List;

class Launcher {

    public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); // Загрузка файла с биновами

            PersonDAO personDAO = (PersonDAO) context.getBean("customerDAO"); // Загрузка бина доступа к таблице клиентов 

            personDAO.deleteAll(); // Удаление всех записей
            
            Person person = new Person("Sergey", "Talipov", 39); // Создание нового объекта таблицы клиентов 
            personDAO.insert(person); // Вставить новый объект (запись) в таблицу клиентов

            personDAO.insert(new Person("Sergey", "Talin", 33)); // Вставить новый объект (запись) в таблицу клиентов
            personDAO.insert(new Person("Pavel", "Borovoi", 28)); // Вставить новый объект (запись) в таблицу клиентов

            Person person1 = personDAO.findByAge(28); // Поиск записи по возрасту клиента
            System.out.println(person1 != null ? person1 : "Нет данных"); // Вывод на экран найденной записи

            personDAO.deleteByLastName("bor"); // Удаление записей по фрагменту фамилии
            personDAO.delete("Sergey", "Talin"); // Удалениезаписи пл имени и фамилии

            List<Person> persons = personDAO.findByFirstName("ser"); // Поиск записей по фрагменту имени
            System.out.println(persons != null ? persons : "Нет данных");

            personDAO.append("Lars", "Vogel", 18); // Добавлние записей
            personDAO.append("Jim", "Knopf", 25);
            personDAO.append("Lars", "Man", 33);
            personDAO.append("Spider", "Man", 44);

            personDAO.update("Knopf", "Talipov"); // Изменение записей в таблице

            System.out.println("Данные в таблице БД:");

            List<Person> list = personDAO.selectAll();
            for (Person myPerson : list) {
                System.out.println(myPerson.getFirstName() + " " + myPerson.getLastName() + " " + myPerson.getAge());
            }

            System.out.println("Вывод записей с имением Lars и фамилией Vogel:");

            list = personDAO.select("Lars", "Vogel");
            for (Person myPerson : list) {
                System.out.println(myPerson.getFirstName() + " " + myPerson.getLastName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error!");
        }
    }
}
