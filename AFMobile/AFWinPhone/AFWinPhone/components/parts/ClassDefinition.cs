using System;
using System.Collections.Generic;

namespace AFWinPhone.components.parts
{
    public class ClassDefinition
    {
        private String className;
        private LayoutProperties layout;
        private List<FieldInfo> fieldInfos;
        private List<ClassDefinition> innerClasses;

        public ClassDefinition(String className)
        {
            this.className = className;
        }

        public void addFieldInfo(FieldInfo field)
        {
            if (fieldInfos == null)
            {
                fieldInfos = new List<FieldInfo>();
            }
            fieldInfos.Add(field);
        }

        public void addInnerClass(ClassDefinition innerClass)
        {
            if (innerClasses == null)
            {
                innerClasses = new List<ClassDefinition>();
            }
            innerClasses.Add(innerClass);
        }

        //GETTERS AND SETTERS

        public void setClassName(String className)
        {
            this.className = className;
        }

        public String getClassName()
        {
            return this.className;
        }

        public void setLayout(LayoutProperties properties)
        {
            this.layout = properties;
        }

        public LayoutProperties getLayout()
        {
            return this.layout;
        }

        public List<FieldInfo> getFieldInfos()
        {
            return this.fieldInfos;
        }

        public void setFieldInfos(List<FieldInfo> fieldInfos)
        {
            this.fieldInfos = fieldInfos;
        }

        public List<ClassDefinition> getInnerClasses()
        {
            return this.innerClasses;
        }



    }
}
