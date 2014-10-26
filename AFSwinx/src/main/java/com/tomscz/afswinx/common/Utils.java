package com.tomscz.afswinx.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * This class provide utilize methods.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public final class Utils {

    /**
     * This method convert String to Integer if value is null then {@link IllegalArgumentException}
     * is thrown.
     * 
     * @param value to convert to Integer
     * @return integer value of String value given in parameter
     * @throws IllegalArgumentException if value is null or conversion is unsuccessful
     */
    public static Integer convertStringToInteger(String value) throws IllegalArgumentException {
        Integer intValue = null;
        if (value == null) {
            throw new IllegalArgumentException();
        }
        intValue = Integer.parseInt(value);
        return intValue;
    }

    /**
     * This method read all from input stream and return {@link StringBuilder} which holds all line
     * form input stream
     * 
     * @param inputStream input stream which holds data to read
     * @return StringBuilder which contains all data from input stream
     * @throws IOException if during creation phase or read is thrown error
     */
    public static StringBuilder readInputSteam(InputStream inputStream) throws IOException {
        StringBuilder responseStrBuilder = new StringBuilder();
        BufferedReader streamReader;
        streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line;
        // Read data
        while ((line = streamReader.readLine()) != null) {
            responseStrBuilder.append(line);
        }
        return responseStrBuilder;
    }

    /**
     * This method create {@link Document} from input file. This document can be parsed by DOM or
     * SAX.
     * 
     * @param file from which will be created document to parse
     * @return Document which will be parsed by DOM or SAX
     * @throws IllegalArgumentException If exception occurs during open file or parsing file then
     *         {@link IllegalArgumentException} is thrown, because input file given in parameter is
     *         illegal
     */
    public static Document buildDocumentFromFile(File file) throws IllegalArgumentException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalArgumentException("This file cannot be parsed." + e.getMessage());
        }
    }

    /**
     * This method parse input string and replaced all #{value} values in hash and bracket by value
     * given in parameters. If value is not found then null is inserted. <br
     * / Example:
     * <ul>
     * <li>inputs : expressionToEvaluate/home/#{user}, parameters: user:martin; result /home/martin</li>
     * <li>inputs : expressionToEvaluate/home/#{user}/#{subdir}, parameters: user:martin,
     * subdir:backup; result /home/martin/backup</li>
     * <li>inputs : expressionToEvaluate/home/#{user}/#{subDir}, parameters: user:martin,
     * subdir:backup; result /home/martin/null</li>
     * <ul>
     * Please note that this parser can't handle with unpairs {} in input parameters
     * 
     * @param expressionToEvaluate this is expression which will be processed and in which will all
     *        value in #{} replaced by value from has map given in second parameter
     * @param parameters which holds value which will be inserted to input string called
     *        expressionToEvaluate
     * @return string which has replaced all #{} values from hash map given as parameter. If value
     *         is not found in hash map then null is inserted
     */
    public static String evaluateElExpression(String expressionToEvaluate,
            HashMap<String, String> parameters) {
        // To chaining string use string builder
        StringBuilder replacedValue = new StringBuilder();
        // Split expression by #{ it gives you strings between and after value which should be
        // replaced
        String[] values = expressionToEvaluate.split("#\\{");
        boolean firstCycle = true;
        for (String value : values) {
            // in first cycle add everything because split is done by #{ which means that before
            // first char sequence #{ is plain text
            if (firstCycle) {
                replacedValue.append(value.substring(0, value.length()));
                firstCycle = false;
                continue;
            }
            // This are values behind string which will be replaced. in first position is string to
            // replaced
            String[] valuesBehind = value.split("\\}");
            // Find replaced value and append it
            String elValue = parameters.get(valuesBehind[0].substring(0, valuesBehind[0].length()));
            replacedValue.append(elValue);
            // If some values left - this means that there is more } brackets ex: #{value}/a}/a}
            // then append them too
            for (int i = 1; i < valuesBehind.length; i++) {
                replacedValue.append(valuesBehind[i]);
                char firstChar = valuesBehind[i].charAt(0);
                //Because split was done by } then it should not be there, then add it if left brackets
                if (firstChar == '{') {
                    replacedValue.append("}");
                }

            }
        }
        return replacedValue.toString();
    }

}
