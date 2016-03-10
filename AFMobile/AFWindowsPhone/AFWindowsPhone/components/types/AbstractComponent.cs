using AFWindowsPhone.enums;
using AFWindowsPhone.rest.holder;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.builders.components.types
{
    interface AbstractComponent
    {
        void insertData(String dataResponse, StringBuilder road);

        SupportedComponents getComponentType();

        bool validateData();

        AFDataHolder reserialize();

    }
}
