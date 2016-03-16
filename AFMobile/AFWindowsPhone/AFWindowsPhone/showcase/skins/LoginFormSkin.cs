using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Media;
using AFWindowsPhone.builders.skins;

namespace AFWindowsPhone.showcase.skins
{
    class LoginFormSkin : DefaultSkin
    {
        public override int getLabelWidth()
        {
            return 200;
        }
    }
}
