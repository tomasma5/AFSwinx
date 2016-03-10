using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.rest.connection
{
    public sealed class SecurityMethod
    {

        public static readonly SecurityMethod BASIC = new SecurityMethod("basic");

        private String name;

        private SecurityMethod(String name)
        {
            this.name = name;
        }

        public bool equalsName(String otherName)
        {
            return (otherName == null) ? false : name.Equals(otherName);
        }

        public String toString()
        {
            return name;
        }
    }
}
