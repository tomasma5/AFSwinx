using AFWindowsPhone.builders;
using AFWindowsPhone.builders.components.types;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone
{
    class AFWindowsPhone
    {
        private static AFWindowsPhone instance = null;
        private Dictionary<String, AFComponent> createdComponents;

        public AFWindowsPhone()
        {
            createdComponents = new Dictionary<String, AFComponent>();
        }

        public static AFWindowsPhone getInstance()
        {
            if (instance == null)
            {
                instance = new AFWindowsPhone();
            }
            return instance;
        }

        public Dictionary<String, AFComponent> getCreatedComponents()
        {
            return createdComponents;
        }

        public void addCreatedComponent(String name, AFComponent component)
        {
            if (createdComponents.ContainsKey(name))
            {
                createdComponents[name] = component;
            }
            else
            { 
                createdComponents.Add(name, component);
            }
        }

        public FormBuilder getFormBuilder()
        {
            return new FormBuilder();
        }

        public ListBuilder getListBuilder()
        {
            return new ListBuilder();
        }

    }
}
