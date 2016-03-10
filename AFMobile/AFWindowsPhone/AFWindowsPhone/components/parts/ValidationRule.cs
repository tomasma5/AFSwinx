using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.builders.components.parts
{
    class ValidationRule
    {
        private String validationType;
        private String value;

        public ValidationRule()
        {
        }

        public String getValidationType()
        {
            return this.validationType;
        }

        public void setValidationType(String validationType)
        {
            this.validationType = validationType;
        }

        public String getValue()
        {
            return this.value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }
    }
}
