using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.enums
{
    class SupportedValidations
    {
        public static readonly SupportedValidations REQUIRED = new SupportedValidations("REQUIRED");
        public static readonly SupportedValidations MAXLENGTH = new SupportedValidations("MAXLENGTH");
        public static readonly SupportedValidations MIN = new SupportedValidations("MIN");
        public static readonly SupportedValidations MAX = new SupportedValidations("MAX");
        public static readonly SupportedValidations LESSTHAN = new SupportedValidations("LESSTHAN");


        private String validationType;

        SupportedValidations(String validationType)
        {
            this.validationType = validationType;
        }

        public String getValidationType()
        {
            return validationType;
        }
    }
}
