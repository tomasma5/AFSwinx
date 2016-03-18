using System;

namespace AFWinPhone.enums
{
    public sealed class LayoutDefinitions
    {
        public static readonly LayoutDefinitions TWOCOLUMNSLAYOUT = new LayoutDefinitions("TWOCOLUMNSLAYOUT", 2);
        public static readonly LayoutDefinitions ONECOLUMNLAYOUT = new LayoutDefinitions("ONECOLUMNLAYOUT", 1);


        private String layoutName;
        private int numberOfColumns;

        LayoutDefinitions(String layoutName, int numberOfColumns)
        {
            this.layoutName = layoutName;
            this.numberOfColumns = numberOfColumns;
        }

        public String getLayoutName()
        {
            return layoutName;
        }

        public int getNumberOfColumns()
        {
            return numberOfColumns;
        }

    }
}
