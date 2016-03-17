using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using Windows.UI.Xaml;
using AFWindowsPhone.builders.skins;

namespace AFWindowsPhone.showcase.skins
{
    class MyAbsencesSkin : DefaultSkin
    {
        public override float getListBorderWidth()
        {
            return 0;
        }

        public override Color getListItemNameColor()
        {
            return Colors.Chocolate;
        }

        public override int getListItemNameSize()
        {
            return 28;
        }

        public override int getListItemsTextSize()
        {
            return 12;
        }

        public override int getListHeight()
        {
            return -1; //see vertical alignment
        }

        public override VerticalAlignment getListVerticalAlignment()
        {
            return VerticalAlignment.Top;
        }
    }
}
