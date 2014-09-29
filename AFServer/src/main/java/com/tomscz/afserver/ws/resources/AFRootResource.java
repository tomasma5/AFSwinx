package com.tomscz.afserver.ws.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.IOUtils;

import com.codingcrayons.aspectfaces.AFWeaver;
import com.codingcrayons.aspectfaces.composition.UIFragmentComposer;
import com.codingcrayons.aspectfaces.configuration.Configuration;
import com.codingcrayons.aspectfaces.configuration.ConfigurationStorage;
import com.codingcrayons.aspectfaces.configuration.Context;
import com.codingcrayons.aspectfaces.configuration.StaticConfiguration;
import com.codingcrayons.aspectfaces.exceptions.AFException;
import com.codingcrayons.aspectfaces.exceptions.AnnotationDescriptorNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.AnnotationNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.AnnotationNotRegisteredException;
import com.codingcrayons.aspectfaces.exceptions.ConfigurationFileNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.ConfigurationNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.ConfigurationParsingException;
import com.codingcrayons.aspectfaces.exceptions.EvaluatorException;
import com.codingcrayons.aspectfaces.metamodel.JavaInspector;
import com.codingcrayons.aspectfaces.metamodel.MetaProperty;
import com.codingcrayons.aspectfaces.plugins.j2ee.AspectFacesListener;
import com.codingcrayons.aspectfaces.plugins.j2ee.configuration.ServerConfiguration;
import com.codingcrayons.aspectfaces.util.Strings;
import com.tomscz.afi.dto.AFClassInfo;
import com.tomscz.afi.exceptions.SkeletonException;
import com.tomscz.afi.inspector.AFRestSwing;
import com.tomscz.afi.ws.mappers.MapperType;

@Path("/")
public class AFRootResource {

    @javax.ws.rs.core.Context HttpServletRequest request;
    
    @Resource
    private WebServiceContext wsContext;
    
    @GET
    @Path("/{param}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getResources(@PathParam("param") String entityClass) {
        
        try {
            AFRestSwing afSwing = new AFRestSwing(request.getSession().getServletContext());
            afSwing.generateSkeleton(entityClass, null, request.getSession().getServletContext());
        } catch (SkeletonException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
