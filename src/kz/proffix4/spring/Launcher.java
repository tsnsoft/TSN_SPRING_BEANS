package kz.proffix4.spring;

import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import java.util.List;
import org.slf4j.LoggerFactory;

class Launcher {

    public static void main(String[] args) {
        // Создание логгера
        Logger logger = LoggerFactory.getLogger(Launcher.class);
        try {
            logger.info("Начало обработки!");

            // Загрузка контекста Spring из файла конфигурации
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

            // Получение бина доступа к таблице клиентов
            PersonDAO personDAO = (PersonDAO) context.getBean("customerDAO");

            // Удаление всех записей из таблицы
            personDAO.deleteAll();

            // Создание и вставка новых записей в таблицу клиентов
            Person person = new Person("Sergey", "Talipov", 39);
            personDAO.insert(person);
            personDAO.insert(new Person("Sergey", "Talin", 33));
            personDAO.insert(new Person("Pavel", "Borovoi", 28));

            // Поиск записи по возрасту клиента и вывод результата
            Person person1 = personDAO.findByAge(28);
            System.out.println(person1 != null ? person1 : "Нет данных");

            // Удаление записей по фрагменту фамилии и по имени и фамилии
            personDAO.deleteByLastName("bor");
            personDAO.delete("Sergey", "Talin");

            // Поиск записей по фрагменту имени и вывод результата
            List<Person> persons = personDAO.findByFirstName("ser");
            System.out.println(persons != null ? persons : "Нет данных");

            // Добавление новых записей в таблицу клиентов
            personDAO.append("Lars", "Vogel", 18);
            personDAO.append("Jim", "Knopf", 25);
            personDAO.append("Lars", "Man", 33);
            personDAO.append("Spider", "Man", 44);

            // Обновление записей в таблице
            personDAO.update("Knopf", "Talipov");

            // Вывод всех данных из таблицы
            System.out.println("Данные в таблице БД:");
            List<Person> list = personDAO.selectAll();
            for (Person myPerson : list) {
                System.out.println(myPerson.getFirstName() + " " + myPerson.getLastName() + " " + myPerson.getAge());
            }

            // Вывод записей с именем Lars и фамилией Vogel
            System.out.println("Вывод записей с именем Lars и фамилией Vogel:");
            list = personDAO.select("Lars", "Vogel");
            for (Person myPerson : list) {
                System.out.println(myPerson.getFirstName() + " " + myPerson.getLastName());
            }

            logger.info("Успех!");

        } catch (Exception e) {
            // Обработка исключений и вывод ошибки в консоль
            e.printStackTrace();
            System.out.println("Error!");
            logger.error("Ошибка обработки!", e);
        }
    }
}
