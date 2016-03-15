using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.enums
{
    public sealed class LayoutOrientation
    {
        public static readonly LayoutOrientation AXISX = new LayoutOrientation("AXISX");
        public static readonly LayoutOrientation AXISY = new LayoutOrientation("AXISY");

        private String orientation;

        LayoutOrientation(String orientation)
        {
            this.orientation = orientation;
        }


        public String getName()
        {
            return this.orientation;
        }
    }
}
