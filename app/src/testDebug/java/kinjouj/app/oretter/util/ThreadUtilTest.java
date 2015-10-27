package kinjouj.app.oretter.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ThreadUtilTest {

    @Test
    public void test_sleep() {
        long start = System.currentTimeMillis();
        ThreadUtil.sleep(1000);
        long end = System.currentTimeMillis();
        assertThat(end - start).isGreaterThan(1000);
    }
}
