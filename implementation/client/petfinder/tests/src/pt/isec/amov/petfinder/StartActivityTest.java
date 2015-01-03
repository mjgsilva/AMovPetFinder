package pt.isec.amov.petfinder;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class pt.isec.amov.petfinder.StartActivityTest \
 * pt.isec.amov.petfinder.tests/android.test.InstrumentationTestRunner
 */
public class StartActivityTest extends ActivityInstrumentationTestCase2<StartActivity> {

    public StartActivityTest() {
        super("pt.isec.amov.petfinder", StartActivity.class);
    }

}
