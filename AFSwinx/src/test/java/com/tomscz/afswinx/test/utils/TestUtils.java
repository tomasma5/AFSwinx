package com.tomscz.afswinx.test.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tomscz.afswinx.common.ParameterMissingException;
import org.junit.Test;

import com.tomscz.afswinx.common.Utils;

/**
 * This is class to tests utils methods.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class TestUtils {

    /**
     * This is test for expression language parser.
     */
    @Test
    public void testStrinELParser() throws ParameterMissingException {
        // Create parameters which will replaced in input string
        HashMap<String, String> parameters = new HashMap<String, String>();
        String id = "23";
        String location = "Prague";
        parameters.put("id", id);
        parameters.put("location", location);
        // Create strings which will be parsed
        List<String> stringToParse = new ArrayList<String>();

        stringToParse.add("/AFServer/rest/Person/#{id}");
        stringToParse.add("/AFServer/rest#{location}/Person/#{id}");
        stringToParse.add("#{location}/AFServer/rest#{location}/Person/#{id}");
        stringToParse.add("{}#{location}{noreplace}is#{id}");
        stringToParse.add("{}#{location}{noreplace}{noreplace}is#{id}");
        stringToParse.add("{}{{{}}}");
        // Create results which should be returned by parser
        List<String> expectedResults = new ArrayList<String>();
        expectedResults.add("/AFServer/rest/Person/" + id);
        expectedResults.add("/AFServer/rest" + location + "/Person/" + id);
        expectedResults.add(location + "/AFServer/rest" + location + "/Person/" + id);
        expectedResults.add("{}" + location + "{noreplace}is" + id);
        expectedResults.add("{}" + location + "{noreplace}{noreplace}is" + id);
        expectedResults.add("{}{{{}}}");
        // On each string use EL parser and compare received string with expected. If doesn't match
        // then fail
        for (int i = 0; i < stringToParse.size(); i++) {
            String result = Utils.evaluateElExpression(stringToParse.get(i), parameters);
            if (!result.equals(expectedResults.get(i))) {
                fail("Expected : " + expectedResults.get(i) + " received: " + result);
            }
        }
    }

    /**
     * Tests what happends when expression language parser should replace something and do not have specified value
     *
     * @throws Exception paramenter missing exception
     */
    @Test(expected = ParameterMissingException.class)
    public void testStringElParsersInvalid() throws Exception{
        HashMap<String, String> parameters = new HashMap<String, String>();
        String id = "23";
        String stringToParse = "#{error}";
        Utils.evaluateElExpression(stringToParse, parameters);
    }

}
