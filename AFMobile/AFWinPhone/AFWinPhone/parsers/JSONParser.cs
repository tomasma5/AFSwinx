using AFWinPhone.components.parts;
using Windows.Data.Json;

namespace AFWinPhone.parsers
{
    interface JSONParser
    {
        ClassDefinition parse(JsonObject toBeParsed);
    }
}
