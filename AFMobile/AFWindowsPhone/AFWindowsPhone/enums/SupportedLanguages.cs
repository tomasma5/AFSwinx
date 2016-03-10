using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.enums
{
    public sealed class SupportedLanguages
    {

        public static readonly SupportedLanguages CZ = new SupportedLanguages("cs", "CZ");
        public static readonly SupportedLanguages EN = new SupportedLanguages("en", "EN");

        private String lang;
        private String country;

        SupportedLanguages(String lang, String country)
        {
            this.lang = lang;
            this.country = country;
        }

        public String getLang()
        {
            return lang;
        }

        public String getCountry()
        {
            return country;
        }
    }
}
