package eu8.bookIt;

import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

@SerenityTest
public class BookItEnvTest {

    @Test
    public void test1(){

        System.out.println(ConfigReader.getProperty("base.url"));
        System.out.println(ConfigReader.getProperty("teacher_email"));
        System.out.println(ConfigReader.getProperty("teacher_password"));
    }

}
