using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AFWindowsPhone.rest.holder;
using Windows.Data.Json;

namespace AFWindowsPhone.rest.connection
{
    class JSONBuilder : BaseRestBuilder
    {
        private static String baseClass = "dummy";

        StringBuilder sb = new StringBuilder();
        AFDataPack dataPack = new AFDataPack(baseClass);
        List<AFDataPack> moreDatas = new List<AFDataPack>();

        public override object reselialize(AFDataHolder componentData)
        {
            JsonObject json = new JsonObject();
            foreach (String key in componentData.getPropertiesAndValues().Keys)
            {
                String value = componentData.getPropertiesAndValues()[key];
                // Check if value is only empty string. if so then set to null, null value can be
                // re-mapped to object but this "" wont
                if (String.IsNullOrWhiteSpace(value))
                {
                    continue;
                }
                json.Add(key, JsonValue.CreateStringValue(value));
            }
            foreach (String childKey in componentData.getInnerClasses().Keys)
            {
                AFDataHolder value = componentData.getInnerClasses()[childKey];
                JsonObject jsonInnerClass = (JsonObject)reselialize(value);
                json.Add(childKey, jsonInnerClass);
            }
            return json;
        }
    }
}
