using System;

namespace AFWinPhone.enums
{
    public sealed class LabelPosition
    {

        public static readonly LabelPosition BEFORE = new LabelPosition("BEFORE");
        public static readonly LabelPosition AFTER = new LabelPosition("AFTER");
        public static readonly LabelPosition NONE = new LabelPosition("NONE");

        private readonly String position;

        LabelPosition(String position)
        {
            this.position = position;
        }

        public String getPosition()
        {
            return position;
        }

    }
}
