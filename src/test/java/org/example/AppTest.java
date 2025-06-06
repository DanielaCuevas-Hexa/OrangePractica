package org.example;

import OrangePages.*;
import Reports.ReportsManager;
import Reports.ScreenshotClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


//Tests
public class AppTest extends BasePage {

    private WebDriver driver;
    private Login loginPage;
    private Properties properties;
    private SearchUserPIM pimSearchPage;

    private ExtentReports extent;
    private ExtentTest test;


    @BeforeTest
    public void setTest() throws IOException {
        //Reportes
        extent = ReportsManager.getInstance();

        //Iniciar firefox
        BasePage basePage = new BasePage();
        driver = basePage.getDriver();
        //Inicializar loginPage
        loginPage = new Login(driver);
        //Cargar propiedades de config.properties
        properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        //Navegar a la URL de la app
        driver.get(properties.getProperty("loginPage.url"));
    }

    @Test
    public void testApp() throws IOException {
        //Reporte
        test = extent.createTest("LOGIN Y GESTION DE EMPLEADO");

        try {
            //1. Hacer Login
            //Obtener credenciales de config.properties e intentar hacer login
            loginPage.inputUsername(properties.getProperty("loginPage.username"));
            loginPage.inputPassword(properties.getProperty("loginPage.password"));
            loginPage.clickBtnLogin();
            System.out.println("SUCCESSFUL LOGIN");
            // Esperar que el dashboard cargue completamente, usando un elemento clave -
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oxd-layout-context']")));
            //Validar login verificando que es redirigido al dashboard
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: You have not been redirected to the dashboard");
            //Screenshot login
            String loginScreenshot = ScreenshotClass.captureScreenshot(driver, "LoginCaptura");
            test.pass("Login con usuario Admin exitoso").addScreenCaptureFromPath("loginSS");

            //2. Agregar empleado
            AddUserPIM addUserPIM = new AddUserPIM(driver);
            addUserPIM.addEmployee(properties);
            String addEmployeeSS = ScreenshotClass.captureScreenshot(driver, "EmpleadoCaptura");
            test.pass("Empleado agregado exitosamente").addScreenCaptureFromPath("addEmployeeSS");

            //3. Buscar empleado
            pimSearchPage = new SearchUserPIM(driver);
            boolean found = pimSearchPage.searchEmployeeByName("Maria");
            Assert.assertTrue(found, "El empleado fue encontrado en los resultados");
            String searchEmployeeSS = ScreenshotClass.captureScreenshot(driver, "BuscarEmpleadoCaptura");
            test.pass("Empleado encontrado exitosamente").addScreenCaptureFromPath("searchSS");

            //4. Hacer Logout
            Logout userLogout = new Logout(driver);
            userLogout.profileLogout();
            test.pass("Logout con nuevo usuario exitoso");

            //5. Hacer Login con ese usuario
            //Obtener credenciales de config.properties e intentar hacer login
            loginPage.inputUsername(properties.getProperty("pim.username"));
            loginPage.inputPassword(properties.getProperty("pim.password"));
            loginPage.clickBtnLogin();
            System.out.println("SUCCESSFUL LOGIN");
            // Nueva espera explícita
            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.urlContains("dashboard"));
            //Validar login verificando que es redirigido al dashboard
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: You have not been redirected to the dashboard");
            String userLoginSS = ScreenshotClass.captureScreenshot(driver, "LoginUsuarioCaptura");
            test.pass("Login con usuario creado exitoso").addScreenCaptureFromPath("newUserLoginSS");

        }catch (Exception e) {
            String screenshotPath = ScreenshotClass.captureScreenshot(driver, "TestFailure");
            test.fail("Test fallido: " + e.getMessage()).addScreenCaptureFromPath(screenshotPath);
            throw e;
        }

    }


    @AfterTest
    public void closeTest() {
        quiteDriver(); //Cierra el navegador
        extent.flush();
    }


}
