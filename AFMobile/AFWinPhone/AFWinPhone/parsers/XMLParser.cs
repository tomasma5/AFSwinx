using AFWinPhone.rest.connection;
using Windows.Data.Xml.Dom;

namespace AFWinPhone.parsers
{
    interface XMLParser
    {
     
        AFSwinxConnectionPack parseDocument(XmlDocument documentToParse);
    }
}
