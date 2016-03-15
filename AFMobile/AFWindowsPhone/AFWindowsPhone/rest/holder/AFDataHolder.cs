using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.rest.holder
{
    class AFDataHolder
    {
        private String className;
        private Dictionary<String, String> propertiesAndValues = new Dictionary<String, String>();
        private Dictionary<String, AFDataHolder> innerClasses = new Dictionary<String, AFDataHolder>();

        public Dictionary<String, AFDataHolder> getInnerClasses()
        {
            return innerClasses;
        }

        public Dictionary<String, String> getPropertiesAndValues()
        {
            return propertiesAndValues;
        }

        public String getClassName()
        {
            return className;
        }

        public void setClassName(String className)
        {
            this.className = className;
        }

        public void addInnerClass(AFDataHolder data)
        {
            innerClasses.Add(data.getClassName(), data);
        }

        public void addPropertyAndValue(String propertyName, String value)
        {
            propertiesAndValues.Add(propertyName, value);
        }

        public AFDataHolder getInnerClassByKey(String key)
        {
            if (innerClasses.ContainsKey(key))
            {
                return innerClasses[key];
            }
            else
            {
                return null;
            }
        }
    }
}
