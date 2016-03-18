using AFWinPhone.components.parts;
using System;
using Windows.UI.Xaml;

namespace AFWinPhone.builders.widgets
{
    interface AbstractWidgetBuilder
    {

        FrameworkElement buildFieldView();

        void setData(AFField field, Object value);

        Object getData(AFField field);
    }
}
