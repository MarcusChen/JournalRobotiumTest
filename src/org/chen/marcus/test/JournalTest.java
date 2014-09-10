package org.chen.marcus.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.widget.ImageView;
import com.robotium.solo.Solo;
import org.chen.marcus.MainActivity;

public class JournalTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public JournalTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    @Smoke
    public void testLogin() throws Exception {
        assertTrue(solo.searchButton("Login", false));
        solo.clickOnEditText(0);
        solo.clearEditText(0);
        solo.enterText(0, "tester");
        assertTrue(solo.searchButton("Login", false));
        solo.enterText(1, "password");
        assertTrue(solo.searchButton("Login", true));
        solo.clickOnCheckBox(0);
        solo.clickOnButton("Login");
        assertTrue(solo.searchButton("Next", false));
    }

    @Smoke
    public void testJournalEntry() throws Exception {
        login();
        assertTrue(solo.searchButton("Next", false));
        solo.enterText(0, "It's a great day to be alive.");
        assertTrue(solo.searchButton("Next", true));
        solo.clickOnButton("Next");
    }

    @Smoke
    public void testMetadataPageButtons() throws Exception {
        login();
        solo.enterText(0, "It's a great day to be alive.");
        solo.clickOnButton("Next");
        assertTrue(solo.searchButton("Take Photo", false));
        assertTrue(solo.searchButton("Previous", true));
        assertTrue(solo.searchButton("Done", false));
        solo.clickOnRadioButton(0);
        assertTrue(solo.searchButton("Take Photo", true));
        assertTrue(solo.searchButton("Previous", true));
        assertTrue(solo.searchButton("Done", true));
    }

    @Smoke
    public void testTakePhoto() throws Exception {
        login();
        solo.enterText(0, "It's a great day to be alive.");
        solo.clickOnButton("Next");
        solo.clickOnRadioButton(0);
        ImageView photo1 = solo.getImage(0);
        assertTrue(photo1.getDrawable() == null);
        solo.clickOnButton("Take Photo");
        Thread.sleep(1000);
        assertTrue(photo1.getDrawable() != null);
    }

    private void login() {
        solo.clickOnEditText(0);
        solo.clearEditText(0);
        solo.enterText(0, "tester");
        solo.enterText(1, "password");
        solo.clickOnButton("Login");
    }
}
