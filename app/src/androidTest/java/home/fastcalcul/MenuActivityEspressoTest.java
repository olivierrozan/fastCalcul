package home.fastcalcul;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class MenuActivityEspressoTest {
    @Rule
    public ActivityTestRule<MenuActivity> menu = new ActivityTestRule<MenuActivity>(MenuActivity.class, true) {

        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, MenuActivity.class);

            return result;
        }
    };

    @Test
    public void testModeA_level1() {

        onView(withId(R.id.modeA)).perform(click());
        onView(withId(R.id.level1)).perform(click());
        onView(withId(R.id.dialogButtonOK)).perform(click());

        onView(withId(R.id.sum)).check(matches(withText("10")));

        MainActivity main = mock(MainActivity.class);
        int a = main.level;
        assertEquals(a, 1);
    }

//    @Test
//    public void testModeA_level2() {
//
//        onView(withId(R.id.modeA)).perform(click());
//        onView(withId(R.id.level2)).perform(click());
//        onView(withId(R.id.dialogButtonOK)).perform(click());
//
//        onView(withId(R.id.sum)).check(matches(withText("100")));
//    }
//
//    @Test
//    public void testModeA_level3() {
//
//        onView(withId(R.id.modeA)).perform(click());
//        onView(withId(R.id.level3)).perform(click());
//        onView(withId(R.id.dialogButtonOK)).perform(click());
//
//        Random r = new Random();
//        int sum = r.nextInt(100 - 10) + 10;
//
//        onView(withId(R.id.sum)).check(matches(withText(String.valueOf(sum))));
//    }
//
//    @Test
//    public void testModeA_level4() {
//
//        onView(withId(R.id.modeA)).perform(click());
//        onView(withId(R.id.level4)).perform(click());
//        onView(withId(R.id.dialogButtonOK)).perform(click());
//
//        Random r = new Random();
//        int sum = r.nextInt(1000 - 100) + 100;
//
//        onView(withId(R.id.sum)).check(matches(withText(String.valueOf(sum))));
//    }

}
