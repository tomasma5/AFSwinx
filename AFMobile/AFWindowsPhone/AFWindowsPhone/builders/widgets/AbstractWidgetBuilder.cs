using AFWindowsPhone.builders.components.parts;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;

namespace AFWindowsPhone.builders.widgets
{
    interface AbstractWidgetBuilder
    {

        FrameworkElement buildFieldView();

        void setData(AFField field, Object value);

        Object getData(AFField field);
    }
}
