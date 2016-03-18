using System;

namespace AFWinPhone.enums
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
