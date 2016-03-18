using System;

namespace AFWinPhone.rest.connection
{
    public sealed class HeaderType
    {

        public static readonly HeaderType XML = new HeaderType("application/xml");
        public static readonly HeaderType JSON = new HeaderType("application/json");

        private String name;

        private HeaderType(String name)
        {
            this.name = name;
        }

        public override String ToString()
        {
            return name;
        }
    }
}
