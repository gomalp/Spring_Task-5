package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

public class MainApp {
   public static void main(String[] args) throws SQLException
   {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));
      userService.add(new User("User5", "Lastname5", "user5@mail.ru"));

      userService.add(new Car("BMV", 123));
      userService.add(new Car("LADA", 555));
      userService.add(new Car("AUDI", 333));
      userService.add(new Car("NISSAN", 444));

      List<User> users = userService.listUsers();
      List<Car> cars = userService.listCars();

      users.get(0).setCar(cars.get(0));
      cars.get(0).setUser(users.get(0));
      users.get(1).setCar(cars.get(1));
      cars.get(1).setUser(users.get(1));
      users.get(2).setCar(cars.get(2));
      cars.get(2).setUser(users.get(2));
      users.get(3).setCar(cars.get(3));
      cars.get(3).setUser(users.get(3));

      for (User user : users) {
         userService.update(user);
      }
      // юзеры с машинами уже  в базе

      System.out.println("Пойдем в базу");
      users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         Car car=user.getCar();
         if (car != null) {
            System.out.println("Car model: " + car.getModel());
            System.out.println("Car series: " + car.getSeries());
         } else {
            System.out.println("No car found for this user.");
         }
         System.out.println();
      }

      System.out.println("Запросим User по модели машины и серии");

      String model = "BMV"; // пример модели
      int series = 123; // пример серии
      User userByAuto=userService.getUserByCar(model,series);
      System.out.println("User имеющий авто "+model+", серия"+series+":  "+userByAuto.getFirstName()+", "+userByAuto.getLastName());

      context.close();
   }
}
