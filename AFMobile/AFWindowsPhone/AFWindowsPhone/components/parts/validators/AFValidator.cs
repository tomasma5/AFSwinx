using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.builders.components.parts.validators
{
    interface AFValidator
    {
        bool validate(AFField field, StringBuilder errorMsgs, ValidationRule rule);
    }
}
