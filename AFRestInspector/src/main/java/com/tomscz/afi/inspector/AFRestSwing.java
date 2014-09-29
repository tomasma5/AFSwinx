package com.tomscz.afi.inspector;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

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
import com.tomscz.afi.commons.Constants;
import com.tomscz.afi.commons.FileUtils;
import com.tomscz.afi.dto.AFClassInfo;
import com.tomscz.afi.exceptions.SkeletonException;
import com.tomscz.afi.ws.mappers.MapperType;

public class AFRestSwing implements AFRest {

    private ServletContext servletContext;
    private final String AF_PATH;

    public AFRestSwing(ServletContext servletContext) throws SkeletonException {
        this.servletContext = servletContext;
        this.AF_PATH = getAFPath();
    }

    private String getAFPath() throws SkeletonException {
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
            throw new SkeletonException();
        }
    }

    public AFClassInfo generateSkeleton(String entityClass, MapperType mapper,
            ServletContext servletContext) throws SkeletonException {

        Class<?> instance = null;
        try {
            // TODO crate automatic inspection for class
            String className = "com.tomscz.afserver.persistence.entity." + entityClass;
            if (!className.contains("@")) {
                instance = Class.forName(className);
                String profile = "structure";
                Context context = init(servletContext);
                AFWeaver af = new AFWeaver(profile);
                String widget = inspectAndTranslate(af, instance, context);
                System.out.println(widget);
            } else {
                //TODO Finish it
                String[] classes = className.split("@");
                instance = Class.forName(classes[0]);
                for (int i = 1; i < classes.length; i++) {
                    Field f = instance.getDeclaredField(classes[i]);
                    instance = f.getType();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SkeletonException();
        }
        return null;
    }

    protected Context init(ServletContext contextServlet)
            throws ConfigurationFileNotFoundException, ConfigurationParsingException,
            AnnotationDescriptorNotFoundException {
        InputStream configPropertyStream = contextServlet.getResourceAsStream("/WEB-INF/aspectfaces.properties");
        AFWeaver.init(configPropertyStream);
        AFWeaver.registerAllAnnotations();

        AFWeaver.addConfiguration(
                new ServerConfiguration("structure", contextServlet),
                FileUtils.createTemporaryFile(AF_PATH + Constants.FILE_SWING_MAIN_CONFIG,
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
}
