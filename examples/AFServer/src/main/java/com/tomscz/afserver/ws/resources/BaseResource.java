package com.tomscz.afserver.ws.resources;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;

public class BaseResource {
    @javax.ws.rs.core.Context
    HttpServletRequest request;

    @Resource
    private WebServiceContext wsContext;
}
