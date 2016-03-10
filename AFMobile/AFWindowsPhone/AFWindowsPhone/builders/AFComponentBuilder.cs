using AFWindowsPhone.builders.components;
using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.components.types;
using AFWindowsPhone.builders.skins;
using AFWindowsPhone.enums;
using AFWindowsPhone.parsers;
using AFWindowsPhone.rest;
using AFWindowsPhone.rest.connection;
using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Data.Json;
using Windows.Storage.Streams;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace AFWindowsPhone.builders
{
    abstract class AFComponentBuilder<T>
    {
        private AFSwinxConnectionPack connectionPack;
        private Skin skin;
        private String connectionKey;
        private String componentKeyName;
        private IRandomAccessStream connectionResource;
        private Dictionary<String, String> connectionParameters;

        public AFComponentBuilder<T> initBuilder(String componentKeyName, IRandomAccessStream connectionResource, String connectionKey)
        {
            this.componentKeyName = componentKeyName;
            this.connectionResource = connectionResource;
            this.connectionKey = connectionKey;
            this.skin = new DefaultSkin();
            return this;
        }

        public AFComponentBuilder<T> initBuilder(String componentKeyName, IRandomAccessStream connectionResource,
                         String connectionKey, Dictionary<String, String> connectionParameters)
        {
            this.componentKeyName = componentKeyName;
            this.connectionResource = connectionResource;
            this.connectionKey = connectionKey;
            this.connectionParameters = connectionParameters;
            this.skin = new DefaultSkin();
            return this;
        }

        protected void initializeConnections()
        {
            if (connectionPack == null && connectionKey != null && connectionResource != null)
            {
                ConnectionParser connectionParser =
                        new ConnectionParser(connectionKey, connectionParameters);
                AFSwinxConnectionPack connections =
                        connectionParser.parseDocument(com.tomscz.afswinx.common.Utils
                                .buildDocumentFromFile(connectionResource));
                connectionPack = connections;
            }
            else {
                // Model connection is important if it could be found then throw exception
                throw new Exception(
                        "There is error during building AFSwinxForm. Connection was not specified. Did you used initBuilder method before build?");
            }
        }

        protected void prepareComponent(ClassDefinition classDef, AFComponent component, int numberOfInnerClasses, bool parsingInnerClass, StringBuilder road)
        {
            if (parsingInnerClass)
            {
                numberOfInnerClasses = 0;
            }
            if (classDef != null)
            {
                if (!parsingInnerClass)
                { //set following properties only once at the beginning
                    component.setName(classDef.getClassName());
                    if (classDef.getLayout() != null)
                    {
                        component.setLayoutDefinitions(classDef.getLayout().getLayoutDefinition());
                        component.setLayoutOrientation(classDef.getLayout().getLayoutOrientation());
                    }
                }
                //fieldsView = (TableLayout) buildLayout(classDef, activity);
                FieldBuilder builder = new FieldBuilder();
                foreach (FieldInfo field in classDef.getFieldInfos())
                {
                    if (field.isInnerClass())
                    {
                        String roadBackup = road.ToString();
                        road.Append(classDef.getInnerClasses()[numberOfInnerClasses].getClassName());
                        road.Append(".");
                        prepareComponent(classDef.getInnerClasses()[numberOfInnerClasses], component, numberOfInnerClasses++, true, road);
                        road = new StringBuilder(roadBackup);
                    }
                    else {
                        AFField affield = builder.prepareField(field, road, getActivity(), skin);
                        if (affield != null)
                        {
                            component.addField(affield);
                        }
                    }
                }
            }
            Debug.WriteLine("NUMBER OF ELEMENTS IN COMPONENT " + component.getFields().Count);
        }

        protected AFComponent buildComponent(String modelResponse, SupportedComponents type)
        {
            //TODO popremyslet co s timto
            AFComponent component = AFComponentFactory.getInstance().getComponentByType(type);

            component.setConnectionPack(connectionPack);
            component.setSkin(skin);

            StackPanel componentView = new StackPanel();
            //componentView.setLayoutParams(getSkin().getTopLayoutParams());
            JSONParser parser = new JSONDefinitionParser();
            JsonObject jsonObj = JsonObject.Parse(modelResponse)[(Constants.CLASS_INFO)].GetObject();
            ClassDefinition classDef = parser.parse(jsonObj);
            prepareComponent(classDef, component, 0, false, new StringBuilder());
            FrameworkElement view = buildComponentView(component);
            componentView.Children.Add(view);
            component.setView(componentView);
            return component;
        }

        protected String getModelResponse()
        {
            AFSwinxConnection modelConnection = connectionPack.getMetamodelConnection();
            if (modelConnection != null)
            {
                RequestTask task = new RequestTask(getActivity(), modelConnection.getHttpMethod(), modelConnection.getContentType(),
                        modelConnection.getSecurity(), null, Utils.getConnectionEndPoint(modelConnection));

                Object modelResponse = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //make it synchronous to wait for response
                if (modelResponse is Exception) {
                    throw (Exception)modelResponse;
                }
                return (String)modelResponse;
            }
            else {
                throw new Exception("No model connection available. Did you call initializeConnections() before?");
            }
        }

        protected String getDataResponse()
        {
            AFSwinxConnection dataConnection = connectionPack.getDataConnection();
            if (dataConnection != null)
            {
                RequestTask getData = new RequestTask(getActivity(), dataConnection.getHttpMethod(), dataConnection.getContentType(),
                        dataConnection.getSecurity(), null, Utils.getConnectionEndPoint(dataConnection));
                Object response = getData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
                if (response is Exception)
                {
                    throw (Exception)response;
                }
                return (String)response;
            }
            return null;
        }

        public abstract AFComponent createComponent();

        protected abstract FrameworkElement buildComponentView(AFComponent component);

        public String getComponentKeyName()
        {
            return componentKeyName;
        }

        public void setComponentKeyName(String componentKeyName)
        {
            this.componentKeyName = componentKeyName;
        }

        public Skin getSkin()
        {
            return skin;
        }

        public void setSkin(Skin skin)
        {
            this.skin = skin;
        }

    }
}
