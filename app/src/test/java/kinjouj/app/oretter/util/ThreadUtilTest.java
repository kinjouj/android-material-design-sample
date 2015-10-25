package kinjouj.app.oretter.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import kinjouj.app.oretter.BuildConfig;

import static org.assertj.core.api.Assertions.*;

/*
@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
*/
public class ThreadUtilTest {

    @Test
    public void test_sleep() {
        long start = System.currentTimeMillis();
        ThreadUtil.sleep(1000);
        long end = System.currentTimeMillis();
        assertThat(end - start).isGreaterThan(1000);
    }
}
