package org.agp8x.android.logging.myloggingapplication;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private Logger log;

    @Before
    public void setUp() throws Exception {
        log = LogUtil.getLog(this.getClass());
    }

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("test");
        log.error("i am a log error");
        log.info("i am just informing");
    }


}