package com.wildcodeschool.fr.springandJPAandHibernate;

import com.wildcodeschool.fr.springandJPAandHibernate.entities.User;
import com.wildcodeschool.fr.springandJPAandHibernate.repositories.UserDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.io.Resources.getResource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest {
    private static ChromeDriverService service;
    private WebDriver driver;
    private String ligneUrl = "http://localhost:8080";

    @Autowired
    UserDao userDao;

    @BeforeClass
    public static void openBrowser() throws IOException {
        File file = new File(String.valueOf(ResourceUtils.getFile(getResource(("chromedriver.exe")))));
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(file)
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @Before
    public void createDriver() {
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        driver.manage().window().maximize();
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testDuBoutonSurIndex() throws InterruptedException {
        driver.get(ligneUrl);
        Thread.sleep(3000);
        driver.findElement(By.name("goToList")).click();
        Thread.sleep(2000);
        Assert.assertEquals("http://localhost:8080/personList", driver.getCurrentUrl());
    }

    @Test
    public void testDuBoutonSurPersonList() throws InterruptedException {
        driver.get(ligneUrl + "/personList");
        Thread.sleep(3000);
        driver.findElement(By.name("add")).click();
        Thread.sleep(2000);
        Assert.assertEquals("http://localhost:8080/addPerson", driver.getCurrentUrl());
    }

    @Test
    public void testAjouterEtSupprimerUnUser() throws InterruptedException {
        driver.get(ligneUrl + "/addPerson");
        Thread.sleep(1500);
        driver.findElement(By.id("nom")).sendKeys("testNom");
        Thread.sleep(1000);
        driver.findElement(By.id("prenom")).sendKeys("testPrenom");
        Thread.sleep(1000);
        driver.findElement(By.id("age")).clear();
        driver.findElement(By.id("age")).sendKeys("100");
        Thread.sleep(1000);
        driver.findElement(By.id("submitAdd")).click();
        List<User> myList = userDao.findAll();
        User test = myList.stream().filter(x -> "testNom".equals(x.getFirstName())).findAny().orElse(null);
        Assert.assertTrue(test.getFirstName().equals("testNom"));
        driver.get(ligneUrl + "/personList");
        Thread.sleep(4000);
        driver.findElement(By.id("idSuppr")).sendKeys(test.getId().toString());
        driver.findElement(By.id("suppr")).click();
        Thread.sleep(5000);
        List<User> myList2 = userDao.findAll();
        Assert.assertNull(myList2.stream().filter(x -> "testNom".equals(x.getFirstName())).findAny().orElse(null));
    }
}


