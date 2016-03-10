using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.skins;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;

namespace AFWindowsPhone.builders.widgets
{
    abstract class BasicBuilder : AbstractWidgetBuilder
    {
        private Skin skin;
        private FieldInfo properties;

        public BasicBuilder(Skin skin, FieldInfo properties)
        {
            this.skin = skin;
            this.properties = properties;
        }

        public Skin getSkin()
        {
            return skin;
        }

        public FieldInfo getProperties()
        {
            return properties;
        }

        public abstract FrameworkElement buildFieldView();
        public abstract void setData(AFField field, object value);
        public abstract object getData(AFField field);
    }
}
