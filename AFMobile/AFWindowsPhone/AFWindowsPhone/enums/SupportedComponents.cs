using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.enums
{
    public sealed class SupportedComponents
    {
        public static readonly SupportedComponents FORM = new SupportedComponents("Form");
        public static readonly SupportedComponents LIST = new SupportedComponents("List");

        private String type;

        SupportedComponents(String type)
        {
            this.type = type;
        }

        public String getType()
        {
            return type;
        }
    }
}
