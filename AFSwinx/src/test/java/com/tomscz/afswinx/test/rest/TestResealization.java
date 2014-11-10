package com.tomscz.afswinx.test.rest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import com.google.gson.JsonObject;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.JSONBuilder;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class TestResealization {

    private AFDataHolder dataToTest;

    @Before
    public void init() {
        dataToTest = new AFDataHolder();
        dataToTest.addPropertyAndValue("name", "Martin");
        dataToTest.addPropertyAndValue("lastName", "Tomasek");
        AFDataHolder innerClass = new AFDataHolder();
        innerClass.setClassName("Adress");
        innerClass.addPropertyAndValue("Street", "Repice");
        dataToTest.addInnerClass(innerClass);
    }


    @Test
    public void testJson() {
        System.out.println("testJson started");
        try {
            BaseRestBuilder restBuilder = new JSONBuilder();
            JsonObject json = (JsonObject) restBuilder.reselialize(dataToTest);
            String result = json.toString();
            if (result == null) {
                System.out.print("testJson failed");
                fail();
            }
        } catch (Exception e) {
            System.out.print("testJson failed");
            fail(e.getMessage());
        }
        System.out.println("testJson passed");
    }
}
