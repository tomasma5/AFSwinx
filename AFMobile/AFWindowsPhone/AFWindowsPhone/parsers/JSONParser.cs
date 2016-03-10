using AFWindowsPhone.builders.components.parts;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Data.Json;

namespace AFWindowsPhone.parsers
{
    interface JSONParser
    {
        ClassDefinition parse(JsonObject toBeParsed);
    }
}
