package com.tomscz.afrest.utils;

import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

import com.tomscz.afrest.commons.AFRestUtils;

public class TestEnums {

    @Test
    public void loadAllEnumFilds() {
        HashMap<String, String> enums =
                AFRestUtils.getEnumDataInClass(Highway.class.getName(), "direction");
        if (enums.isEmpty()) {
            fail();
        }
        // Test existed values
        String value = enums.get("NOWAY");
        if (value == null) {
            fail("Value for key NOWAY should be in enum set");
        }
        value = enums.get("BADWAY");
        if (value == null) {
            fail("Value for key BADWAY should be in enum set");
        }
        // Test not existed values, if this value is there then fail
        value = enums.get("NOEXIST");
        if (value != null) {
            fail("Value for key NOEXIST should NOT be in enum set");
        }
    }
}
