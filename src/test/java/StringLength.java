
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringLength {
    @Test
    public void testLength(){

        String str= "Hello, world test!";
        int length= str.length();

        assertTrue(length>=15,"Условия по длинне переменной не соблюдены");
        System.out.println(length);
    }
}