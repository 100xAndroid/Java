package cz.stokratandroid.testovani;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testovaciMetoda_isCorrect() {
        int vysledekTestu = MainActivity.testovaciMetoda(2 , 3);
        assertEquals(5, vysledekTestu);
    }

    @Test
    public void testovaciMetoda_isCorrect2() {
        int vysledekTestu = MainActivity.testovaciMetoda(2 , 3);
        assertEquals(4, vysledekTestu);
    }

}