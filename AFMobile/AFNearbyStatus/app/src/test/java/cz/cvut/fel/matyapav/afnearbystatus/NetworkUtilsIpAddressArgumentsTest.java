package cz.cvut.fel.matyapav.afnearbystatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.util.NetworkUtils;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NetworkUtilsIpAddressArgumentsTest {

    @Parameterized.Parameter()
    public String ipAddress;

    /**
     * Defines parameters for test
     * @return
     */
    @Parameterized.Parameters
    public static Collection ipAddressesForTest() {
        return Arrays.asList(new String[] {
                "127.0.1" },
                "",
                "127.0.0.5.4",
                "127..0.1",
                "127.4888.785.487",
                "-1.-2.10.10",
                "ab.cd.45.eg"
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNextIpAddressBadFormat() throws Exception {
        NetworkUtils.checkIpAddressFormat(ipAddress);
    }
}