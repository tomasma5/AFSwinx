using System;

namespace AFWinPhone.rest.connection
{
    public sealed class SecurityMethod
    {

        public static readonly SecurityMethod BASIC = new SecurityMethod("basic");

        private String name;

        private SecurityMethod(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return name;
        }
    }
}
