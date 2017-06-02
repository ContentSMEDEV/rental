import com.saber.main.services.CarService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.DriverManager;

/**
 * Created by imac6 on 6/2/2017 AD.
 */
public class main {

    public static void main(String [] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        CarService carCRUDService = ctx.getBean("CarService", CarService.class);
        carCRUDService.getCarByID('0');

        System.out.println("hellop");
    }
}
