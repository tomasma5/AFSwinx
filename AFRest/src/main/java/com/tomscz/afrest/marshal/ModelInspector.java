package com.tomscz.afrest.marshal;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.codingcrayons.aspectfaces.AFWeaver;
import com.codingcrayons.aspectfaces.configuration.Context;
import com.codingcrayons.aspectfaces.exceptions.AFException;
import com.codingcrayons.aspectfaces.exceptions.AnnotationDescriptorNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.ConfigurationFileNotFoundException;
import com.codingcrayons.aspectfaces.exceptions.ConfigurationParsingException;
import com.codingcrayons.aspectfaces.metamodel.JavaInspector;
import com.codingcrayons.aspectfaces.plugins.j2ee.configuration.ServerConfiguration;
import com.codingcrayons.aspectfaces.util.Strings;
import com.tomscz.afrest.commons.AFRestConstants;
import com.tomscz.afrest.commons.FileUtils;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

/**
 * This class use <a href="http://www.aspectfaces.com/"> AspectFaces</a> to generate context which
 * could be used to create rest definition of this data. It use
 * {@link ModelInspector#DEFAULT_LAYOUT} {@link ModelInspector#DEFAULT_MAPPING} and
 * {@link ModelInspector#DEFAULT_CLASS_LAYOUT} files to inspect. If you want use another files, then
 * use proper method to generate.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ModelInspector {

    private static final String DEFAULT_MAPPING = "structure.config.xml";
    private static final String DEFAULT_LAYOUT = "templates/structure.xml";
    private static final String DEFAULT_CLASS_LAYOUT = "templates/simpleLayout.xml";
    private static final int DEPTH = 100;

    private HashMap<String, String> classTemplateMapping = new HashMap<String, String>();
    private HashMap<String, String> classStructureMapping = new HashMap<String, String>();
    private HashMap<String, Object> contextVariable = new HashMap<String, Object>();
    private String[] roles;
    private String[] profiles;
    private ServletContext servletContext;
    private String templateMapping;
    private String templateInnerClass;
    private String structureMapping;

    public ModelInspector(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * This method generate model which can be used to create form, widget and etc. Key is name of
     * field and value is name of xml file
     * 
     * @param fullClassName className to generate
     * @param templateMapping template mapping, layout which will be used to root entity
     * @param classStructureMapping this set structure which will be used based on field.
     * @param classTemplateMapping this set layout which will be used based on field.
     * @return model which can be used to create form, tables, etc.
     * @throws MetamodelException if error during generation module occur then this exception is
     *         thrown.
     */
    public AFMetaModelPack generateModel(String fullClassName, String templateMapping,
            HashMap<String, String> classStructureMapping,
            HashMap<String, String> classTemplateMapping) throws MetamodelException {
        this.templateMapping = templateMapping;
        this.classStructureMapping = classStructureMapping;
        this.classTemplateMapping = classTemplateMapping;
        return generateModel(fullClassName);
    }

    /**
     * This method generate model which can be used to create form, widget and etc. Key is name of
     * field and value is name of xml file
     * 
     * @param fullClassName className to generate
     * @param structureMapping structure which will be used.
     * @param templateMapping layout (template) which will be used on root element
     * @param templateInnerClass layout (template) which will be used on non-primitive root children
     * @return model which can be used to create form, tables, etc.
     * @throws MetamodelException if error during generation module occur then this exception is
     *         thrown.
     */
    public AFMetaModelPack generateModel(String fullClassName, String structureMapping,
            String templateMapping, String templateInnerClass) throws MetamodelException {
        this.structureMapping = structureMapping;
        this.templateMapping = templateMapping;
        this.templateInnerClass = templateInnerClass;
        return generateModel(fullClassName);
    }

    /**
     * This method generate model which can be used to create form, widget and etc. Key is name of
     * field and value is name of xml file.
     * 
     * @param fullClassName className to generate
     * @return model which can be used to create form, tables, etc.
     * @throws MetamodelException if error during generation module occur then this exception is
     *         thrown.
     */
    public AFMetaModelPack generateModel(String fullClassName) throws MetamodelException {
        String template = getLayout(fullClassName, true);
        String mapping = getMapping(fullClassName);
        String resultMapping = generate(fullClassName, template, mapping);
        // This flag hold information if there are some other class to inspect false = there are
        boolean isDone = false;
        // This hold number of inspect which were done.
        int numberOfRounds = 0;
        Document doc = XMLParseUtils.transformStringToXml(resultMapping);
        // Start inspect inner classes
        while ((!isDone && (DEPTH > numberOfRounds))) {
            try {
                // Find all un-inspected class
                NodeList entitiesClasses = doc.getElementsByTagName(XMLParseUtils.ENTITYCLASS);
                if (entitiesClasses.getLength() <= 0) {
                    // if there are no class to inspect then end
                    isDone = true;
                }
                for (int i = 0; i < entitiesClasses.getLength(); i++) {
                    // Get entity to inspect
                    Node node = entitiesClasses.item(i);
                    String innerClassToTransform = null;
                    String fieldName = null;
                    for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                        Node n = node.getChildNodes().item(j);
                        // Find their inner class - it must be canoic name
                        if (n.getNodeName().equals(XMLParseUtils.ENTITYFIELDTYPE)) {
                            innerClassToTransform = n.getTextContent();
                        } else if (n.getNodeName().equals(XMLParseUtils.FIELDNAME)) {
                            // Find their fieldName
                            fieldName = n.getTextContent();
                        }
                    }
                    // Determine which template will be used based on filedName
                    String innerTemplate = getLayout(fieldName, false);
                    // Determine which structure mapping will be used based on fieldName
                    String innerMapping = getMapping(fieldName);
                    // Inspect class, this will generate widget on this class
                    String partialResult =
                            generate(innerClassToTransform, innerTemplate, innerMapping);
                    Document partialDocument = XMLParseUtils.transformStringToXml(partialResult);
                    // Find root class of inspected class
                    NodeList classes =
                            partialDocument.getElementsByTagName(XMLParseUtils.ROOTCLASS);
                    Node newClass = classes.item(0);
                    Element newClasselement = (Element) newClass;
                    newClasselement.setAttribute("id", fieldName);
                    newClass = (Node) newClasselement;
                    Node parent = node.getParentNode();
                    // Replace it, we replace not inspected class by its inspected content
                    parent.replaceChild(doc.adoptNode(newClass), node);
                    // Add fieldName element
                    Element fieldNameElement = doc.createElement(XMLParseUtils.FIELDNAME);
                    fieldNameElement.setTextContent(fieldName);
                    newClass.appendChild(fieldNameElement);
                }
            } catch (MetamodelException e) {
                isDone = true;
            }
            numberOfRounds++;
        }
        return generateFullModel(doc);
    }

    /**
     * This method generate inspect class and generate widget based on template and mapping in
     * parameter.
     * 
     * @param fullClassName
     * @param layout which will be used its XML file name
     * @param structure which will be used. its XML file name
     * @return fully inspected class based on structure and layout
     * @throws MetamodelException if during inspection occur error.
     */
    private String generate(String fullClassName, String layout, String structure)
            throws MetamodelException {
        Class<?> instance = null;
        try {
            instance = Class.forName(fullClassName);
            Context context = init(servletContext, structure);
            context.setLayout(layout);
            AFWeaver af = new AFWeaver(structure);
            String widget = inspectAndTranslate(af, instance, context);
            return widget;
        } catch (Exception e) {
            throw new MetamodelException(e.getMessage());
        }
    }

    private AFMetaModelPack generateFullModel(Document modelDefinition) throws MetamodelException {
        ModelBuilder builder = new MetaModelBuilder(modelDefinition);
        AFMetaModelPack generatedInfo = builder.buildModel();
        return generatedInfo;
    }

    private Context init(ServletContext contextServlet, String mapping)
            throws ConfigurationFileNotFoundException, ConfigurationParsingException,
            AnnotationDescriptorNotFoundException, IOException {

        AFWeaver.addConfiguration(new ServerConfiguration(mapping, contextServlet), FileUtils
                .createTemporaryFile(AFRestConstants.ASPECT_FACES_RESOURCE_ROOT_FOLDER + mapping,
                        AFRestConstants.XML_FILE_TYPE, contextServlet), false, true);

        Context context = new Context(null);
        context.setUseCover(true);
        context.setCollate(true);
        if(contextVariable != null){
            for(String key: contextVariable.keySet()){
                context.getVariables().put(key, contextVariable.get(key));
            }
        }
        if(roles != null){
            context.setRoles(roles);
        }
        if(profiles != null){
            context.setProfiles(profiles);
        }
        return context;
    }

    private String inspectAndTranslate(AFWeaver afWeaver, Class<?> clazz, Context context)
            throws AFException, IOException {
        afWeaver.setInspector(new JavaInspector(clazz));
        context.setFragmentName(makeName(clazz, context));
        return afWeaver.generate(context);
    }

    private String makeName(Class<?> clazz, Context context) {
        return Strings.lowerFirstLetter(clazz.getSimpleName());
    }

    /**
     * This method return structure which will be used based on fieldName
     * 
     * @param fieldName which will determine which structure will be used.
     * @return structure name as XML file which will be used based on fieldName
     */
    private String getMapping(String fieldName) {
        if (classStructureMapping != null && !classStructureMapping.isEmpty()
                && classStructureMapping.get(fieldName) != null) {
            return classStructureMapping.get(fieldName);
        }
        if (structureMapping != null && !structureMapping.isEmpty()) {
            return structureMapping;
        }
        return DEFAULT_MAPPING;
    }

    /**
     * This method return layout which will be used based on fieldName and variable isRoot. This
     * variable (isRoot) determine if this is first inspection of root class, hence you want to use
     * specific layout for this
     * 
     * @param fieldName which will determine which layout will be used.
     * @param isRoot
     * @return layout name as XML file which will be used based on fieldName
     */
    private String getLayout(String fieldName, boolean isRoot) {
        if (isRoot && (classTemplateMapping == null || classTemplateMapping.isEmpty()
                || classTemplateMapping.get(fieldName) == null) && (templateMapping == null || templateMapping.isEmpty())) {
            return DEFAULT_LAYOUT;
        }
        if (classTemplateMapping != null && !classTemplateMapping.isEmpty()
                && classTemplateMapping.get(fieldName) != null) {
            return classTemplateMapping.get(fieldName);
        }
        if (templateMapping != null && !templateMapping.isEmpty()) {
            return templateMapping;
        }
        if (templateInnerClass != null && !templateInnerClass.isEmpty()) {
            return templateInnerClass;
        }
        return DEFAULT_CLASS_LAYOUT;
    }

    public HashMap<String, Object> getContextVariable() {
        return contextVariable;
    }

    public void setContextVariable(HashMap<String, Object> contextVariable) {
        this.contextVariable = contextVariable;
    }

    public String[] getProfiles() {
        return profiles;
    }

    public void setProfiles(String[] profiles) {
        this.profiles = profiles;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
    
}
