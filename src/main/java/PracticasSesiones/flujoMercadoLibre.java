package PracticasSesiones;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class flujoMercadoLibre
{
    public static void main( String[] args ) throws InterruptedException
    {
        //Declaramos una variable driver de tipo WebDriver e inicializamos el driver para usar el navegador firefox
        WebDriver driver = new FirefoxDriver();

        //Obtenemos la interfaz para navegar y nos dirigimos a la url
        driver.navigate().to("https://www.mercadolibre.com.mx/");

        //Assert para verificar que se llego a la pagina correcta
        Assert.assertEquals("https://www.mercadolibre.com.mx/", driver.getCurrentUrl(), "ERROR, la pagina no es la indicada");

        //Declaramos una variable de tipo WebElement, esto igual a: buscar UN elemento de la pagina, lo especificamos por el atributo id
        WebElement searchBar = driver.findElement(By.id("cb1-edit"));

        //Declaramos una variable string que sera nuestro item a buscar
        String searchItem = "samsung";

        //Mandamos o simulamos la escritura de nuestro item a la variable que hicimos para la barra de busqueda
        searchBar.sendKeys(searchItem);

        //Declaramos una variable WebElement, esto igual a: buscar UN elemento de la pagina, lo especificamos por el atributo id
        WebElement Btn = driver.findElement(By.xpath(".//button[contains(@class, 'nav-search')]"));
        //Simulamos un click a esa variable
        Btn.click();

        //Pausa la ejecucion del programa por 3 segundos
        Thread.sleep(3000);

        //Assert para verificar que el titulo de la pagina de resultados contiene el texto de nuestra variable
        Assert.assertTrue(driver.getTitle().toLowerCase().contains(searchItem.toLowerCase()), "ERROR, El titulo de la pagina de resultados no contiene el termino buscado");

        //Declaramos una variable WebElement, esto igual a: buscar UN elemento de la pagina, lo especificamos por el atributo id
        WebElement helpBtn = driver.findElement(By.xpath(".//a[@class='nav-menu-item-link'][text()='Ayuda']"));
        //Simulamos un click a esa variable
        helpBtn.click();

        //Pausa la ejecucion del programa por 3 segundos
        Thread.sleep(3000);

        //Mandamos a imprimir el titulo de la pagina
        System.out.println(driver.getTitle());

        //Declaramos una variable WebElement, esto igual a: buscar UN elemento de la pagina, lo especificamos por el atributo id
        WebElement homeBtn = driver.findElement(By.xpath(".//a[contains(text(),'Mercado Libre México - Donde comprar y vender de t')]"));
        //Simulamos un click a esa variable
        homeBtn.click();

        //Cierra sesion del driver
        driver.quit();



















    }
}

