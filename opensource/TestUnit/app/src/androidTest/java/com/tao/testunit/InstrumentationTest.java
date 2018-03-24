package com.tao.testunit;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by SDT14324 on 2017/11/1.
 */

public class InstrumentationTest {
    private static final String STRING_TO_BE_TYPED = "itach";
    @Rule
    public ActivityTestRule<UnitTestActivity> activityTestRule = new ActivityTestRule<UnitTestActivity>(UnitTestActivity.class);

    @Test
    public void sayHello(){
        onView(withId(R.id.uta_edit)).perform(typeText("itach"), closeSoftKeyboard()); //line 1

        onView(withText("打招呼")).perform(click()); //line 2

        String expectedText = "Hello," + "itach";
        onView(withId(R.id.uta_text)).check(matches(withText(expectedText))); //line 3
    }

}
