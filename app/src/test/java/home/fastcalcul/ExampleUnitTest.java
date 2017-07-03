package home.fastcalcul;

import android.os.CountDownTimer;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Test
    public void test() {
        MainActivity main = new MainActivity();

        System.out.println(String.valueOf(main.numberOfGoodAnswers));
    }
}