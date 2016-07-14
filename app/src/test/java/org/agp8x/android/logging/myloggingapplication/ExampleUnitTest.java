package org.agp8x.android.logging.myloggingapplication;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    static {
        Util.configLog();
    }

    private Logger log;

    @Before
    public void setUp() throws Exception {
        //Util.configLogByFiles();
        Util.configLog();
        log = LoggerFactory.getLogger(ExampleUnitTest.class);

    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.println("test");
        log.error("i am a log error");
        log.info("i am just informing");
    }


}