using AFWinPhone.enums;
using AFWinPhone.rest.holder;
using System;
using System.Text;

namespace AFWinPhone.components.types
{
    interface AbstractComponent
    {
        void insertData(String dataResponse, StringBuilder road);

        SupportedComponents getComponentType();

        bool validateData();

        AFDataHolder reserialize();

    }
}
