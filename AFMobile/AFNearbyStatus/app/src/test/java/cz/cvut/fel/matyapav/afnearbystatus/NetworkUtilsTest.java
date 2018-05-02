package cz.cvut.fel.matyapav.afnearbystatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.robolectric.RobolectricTestRunner;

import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.util.NetworkUtils;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class NetworkUtilsTest {

    @Test
    public void testNextIpAddressHappy() throws Exception {
        String ipAddress = "127.0.0.1";
        String expected = "127.0.0.2";
        String actual = NetworkUtils.nextIpAddress(ipAddress);
        assertEquals(actual, expected);
    }

    @Test
    public void testNextIpAddressOver255() throws Exception {
        String ipAddress = "127.0.0.255";
        String expected = "127.0.1.0";
        String actual = NetworkUtils.nextIpAddress(ipAddress);
        assertEquals(actual, expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextIpAddressAddressNull() throws Exception {
        NetworkUtils.nextIpAddress(null);
    }

    @Test
    public void testGetRangeFromMaskHappy() throws Exception {
        String mask = "255.255.254.0";
        int expected = 2*256 - 2;
        int actual = NetworkUtils.getRangeFromMask(mask);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRangeFromMaskNull() throws Exception {
        NetworkUtils.getRangeFromMask(null);
    }

}