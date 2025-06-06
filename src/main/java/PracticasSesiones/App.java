package PracticasSesiones;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import java.util.List;
import static java.lang.Thread.sleep;


/**
 * Daniela Cuevas
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
    {
        //Declaramos una variable driver de tipo WebDriver e inicializamos el driver para usar el navegador firefox
        WebDriver driver = new FirefoxDriver();

        //Obtenemos la interfaz para navegar y nos dirigimos a la url
        driver.navigate().to("https://www.amazon.com.mx/");

        //Declaramos una variable de tipo WebElement, esto igual a: buscar UN elemento de la pagina, lo especificamos por el atributo id
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));

        //Declaramos una variable string que sera nuestro item a buscar
        String searchTerm = "iphone";

        //Mandamos o simulamos la escritura de nuestro item a la variable que hicimos para la barra de busqueda
        searchBox.sendKeys(searchTerm);

        //Declaramos una variable WebElement, esto igual a: buscar UN elemento de la pagina, lo especificamos por el atributo id
        WebElement searchBtn = driver.findElement(By.id("nav-search-submit-button"));
        //Simulamos un click a esa variable
        searchBtn.click();

        //Pausa la ejecucion del programa por 3 segundos
        Thread.sleep(3000);

        //Mandamos a imprimir el titulo de la pagina
        System.out.println(driver.getTitle());

        //Assert: usado para verificar si una condicion es verdadera
        //Verifica si el  titulo de la pagina contiene el nombre que pusimos a nuestra variable
        Assert.assertTrue(driver.getTitle().contains(searchTerm));

        //Creamos una lista de elements web que encuentre con la xpath colocada
        List<WebElement> results = driver.findElements(By.xpath("(//div[contains(@class, 'puis-card-container')]"));

        //Imprime el numero de resultados encontrados
        System.out.println("Results: " + results.size());

        //Verifica que la lista no este vacia
        Assert.assertFalse(results.isEmpty());

        //creamos un contador para los resultados
        int correctResults = 0;

        //Recorremos la lista
        for (WebElement result : results) {
            //Creamos una variable titulo que almacene el resultado del xpath
            WebElement title = result.findElement(By.xpath(".//a/h2//span"));
            //Si no es nulo
            if(title != null) {
                //Entonces imprime title
                System.out.println("Result: " + title.getText());
                //Si el titulo que pasamos a minusculas contiene la variable searchTerm en minusculas
                if(title.getText().toLowerCase().contains(searchTerm.toLowerCase())) {
                    //Entonces el contador aumenta en 1
                    correctResults++;
                }
            }
        }
        //Imprime el numero de resultados correctos
        System.out.println("Results containing text: " + correctResults);
        //Cierra sesion del driver
        driver.quit();
        }
    }
}