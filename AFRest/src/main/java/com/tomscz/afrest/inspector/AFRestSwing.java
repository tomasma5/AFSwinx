package com.tomscz.afrest.inspector;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.codingcrayons.aspectfaces.AFWeaver;
import com.codingcrayons.aspectfaces.configuration.Context;
import com.codingcrayons.aspectfaces.exceptions.AFException;
import com.codingcrayons.aspectfaces.exceptions.AnnotationDescriptorNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.ConfigurationFileNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.ConfigurationParsingException;
import com.codingcrayons.aspectfaces.metamodel.JavaInspector;
import com.codingcrayons.aspectfaces.plugins.j2ee.configuration.ServerConfiguration;
import com.codingcrayons.aspectfaces.properties.PropertyLoader;
import com.codingcrayons.aspectfaces.util.Strings;
import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.commons.Constants;
import com.tomscz.afrest.commons.FileUtils;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.marshal.ModelBuilder;
import com.tomscz.afrest.marshal.ModelFactory;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afrest.rest.dto.data.AFDataPack;

public class AFRestSwing implements AFRest {

    private ServletContext servletContext;
    private final String AF_PATH;

    public AFRestSwing(ServletContext servletContext) throws AFRestException {
        this.servletContext = servletContext;
        this.AF_PATH = getAFPath();
    }

    private String getAFPath() throws AFRestException {
        InputStream propertyStream =
                servletContext.getResourceAsStream(Constants.WEB_INF_FOLDER
                        + Constants.ASPECT_FACES_PROPERTY_FILE);
        PropertyLoader loader;
        try {
            loader = new PropertyLoader(propertyStream);
            String filesDirectory = loader.getProperty(Constants.FILE_DIRECTORY_PROPERTY);
            if (filesDirectory == null || filesDirectory.isEmpty()) {
                filesDirectory = Constants.ASPECT_FACES_RESOURCE_ROOT_FOLDER;
            }
            return filesDirectory;
        } catch (ConfigurationParsingException e) {
            // TODO Auto-generated catch block
            throw new AFRestException();
        }
    }


    @Override
    public AFMetaModelPack generateSkeleton(String fullClassName,
            ServletContext servletContext) throws AFRestException {
        Class<?> instance = null;
        try {
                instance = Class.forName(fullClassName);    
                String profile = "structure";
                Context context = init(servletContext);
                context.setLayout("templates/structure.xml");
                AFWeaver af = new AFWeaver(profile);
                String widget = inspectAndTranslate(af, instance, context);
                System.out.println(widget);
                ModelBuilder builder =
                        new ModelFactory().createModelBuilder(SupportedComponents.FORM, widget);
                try {
                    AFMetaModelPack generatedInfo = builder.buildModel();
                    return generatedInfo;
                } catch (MetamodelException e) {
                    e.printStackTrace();
                }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new AFRestException();
        }
        return null;
    }

    protected Context init(ServletContext contextServlet)
            throws ConfigurationFileNotFoundException, ConfigurationParsingException,
            AnnotationDescriptorNotFoundException {
        InputStream configPropertyStream =
                contextServlet.getResourceAsStream("/WEB-INF/aspectfaces.properties");
        AFWeaver.init(configPropertyStream);
        AFWeaver.registerAllAnnotations();

        AFWeaver.addConfiguration(new ServerConfiguration("structure", contextServlet), FileUtils
                .createTemporaryFile(AF_PATH + Constants.FILE_SWING_MAIN_CONFIG,
                        Constants.XML_FILE_TYPE, contextServlet), false, true);

        Context context = new Context(null);
        context.setUseCover(true);
        context.setCollate(true);
        return context;
    }

    protected String inspectAndTranslate(AFWeaver afWeaver, Class<?> clazz, Context context)
            throws AFException, IOException {

        afWeaver.setInspector(new JavaInspector(clazz));
        context.setFragmentName(makeName(clazz, context));
        return afWeaver.generate(context);
    }

    protected String makeName(Class<?> clazz, Context context) {
        return Strings.lowerFirstLetter(clazz.getSimpleName());
    }

    @Override
    public AFDataPack generateDataObject(Class<?> clazz, Object objectToGenerate)
            throws IllegalArgumentException {
        if (clazz == null || objectToGenerate == null) {
            throw new IllegalArgumentException("Object to generated and class cant be null");
        }
        AFDataPack data = new AFDataPack(clazz.getName());
        List<Method> getters = getGetters(objectToGenerate.getClass());
        for (int i = 0; i < getters.size(); i++) {
            try {
                String value = String.valueOf(getters.get(i).invoke(objectToGenerate));
                if (value != null && !value.equals("null")) {
                    String key = getters.get(i).getName();
                    char firstLetter = Character.toLowerCase(key.charAt(3));
                    key = key.substring(4);
                    key = firstLetter + key;
                    AFData concreteData = new AFData(key, value);
                    data.addData(concreteData);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return data;
    }

    private List<Method> getGetters(@SuppressWarnings("rawtypes") Class clazz)
            throws SecurityException {
        List<Method> getters = new ArrayList<Method>();

        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
                getters.add(method);
            }
        }
        return getters;
    }
}
