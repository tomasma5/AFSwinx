using AFWindowsPhone.builders.components.types;
using AFWindowsPhone.enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.builders.components
{
    class AFComponentFactory
    {
        private static AFComponentFactory instance = null;

        public static AFComponentFactory getInstance()
        {
            if (instance == null)
            {
                instance = new AFComponentFactory();
            }
            return instance;
        }

        public AFComponent getComponentByType(SupportedComponents type)
        {
            if (type.Equals(SupportedComponents.FORM))
            {
                return new AFForm();
            }
            else if (type.Equals(SupportedComponents.LIST))
            {
                return new AFList();
            }
            else {
                ///type not supported;
                return null;
            }

        }
    }


}
