package com.tomscz.afserver.ws.json;

import javax.ws.rs.ext.ContextResolver;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public JacksonConfig() {
        objectMapper = new ObjectMapper();
        // I am not sure which format we will use, so far we will use ISO
        // objectMapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }

}
