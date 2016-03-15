using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.enums
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
