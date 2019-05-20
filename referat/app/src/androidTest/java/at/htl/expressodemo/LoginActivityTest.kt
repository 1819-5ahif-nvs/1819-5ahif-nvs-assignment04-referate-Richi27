package at.htl.expressodemo

import android.support.v7.widget.AppCompatEditText
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {
    private val invalid_email = "fooexample.com"
    private val valid_email = "foo@example.com"
    private val invalid_password = "0"
    private val correct_password = "hello"
    private val incorrect_password = "passme01"

    // ermöglicht den zugriff auf die actvity und dessen resourcen wie methoden,properties ect...
    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)


    @Test
    fun attemptToLoginWithInvalidEmail() {
        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(invalid_email))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(correct_password), ViewActions.closeSoftKeyboard())
        // click ermittelt die koordinaten vom button und klickt drauf
        // klickt dabei auf die Tastatur(die als eigene application erkannt wird) bevor sie geschlossen wird, ERROR:
        // SecurityException: Injecting to another application requires INJECT_EVENTS permission
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.email))
            .check(ViewAssertions.matches(withEditTextError(activityRule.activity.getString(R.string.error_invalid_email))))
    }

    @Test
    fun attemptToLoginWithInvalidPassword() {
        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(valid_email))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(invalid_password), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .check(ViewAssertions.matches(withEditTextError(activityRule.activity.getString(R.string.error_invalid_password))))
    }

    @Test
    fun attemptToLoginWithNoEmailAndPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.email))
            .check(ViewAssertions.matches(withEditTextError(activityRule.activity.getString(R.string.error_field_required))))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .check(ViewAssertions.matches(withEditTextError(activityRule.activity.getString(R.string.error_field_required))))
    }

    @Test
    fun attemptToLoginWithIncorrectPassword() {


        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(valid_email))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(incorrect_password), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .check(ViewAssertions.matches(withEditTextError(activityRule.activity.getString(R.string.error_incorrect_password))))

        Espresso.onView(ViewMatchers.withId(R.id.login_result))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.login_fail)))
    }

    @Test
    fun attemptToLoginWithCorrectData() {
        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(valid_email))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(correct_password), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.login_result))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.login_success)))
    }

    fun withEditTextError(error: String): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                return (view as AppCompatEditText).error == error
            }

            override fun describeTo(description: Description) {
                description.appendText("TextInputLayout should have $error")
            }
        }
    }
}

