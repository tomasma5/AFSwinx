using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using AFWindowsPhone.builders.skins;

namespace AFWindowsPhone.showcase.skins
{
    class AbsenceManagementSkin : DefaultSkin
    {

        public override int getLabelWidth()
        {
            return 70;
        }

        public override int getInputWidth()
        {
            return 120;
        }

        public override int getLabelFontSize()
        {
            return 18;
        }

        public override int getFieldFontSize()
        {
            return 18;
        }

        public override bool isListItemNameLabelVisible()
        {
            return false;
        }

    }
}
