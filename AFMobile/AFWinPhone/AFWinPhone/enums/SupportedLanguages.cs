using System;

namespace AFWinPhone.enums
{
    public sealed class SupportedLanguages
    {

        public static readonly SupportedLanguages CZ = new SupportedLanguages("cs", "CZ");
        public static readonly SupportedLanguages EN = new SupportedLanguages("en", "US");

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
