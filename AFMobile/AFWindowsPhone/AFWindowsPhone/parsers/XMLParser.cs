using AFWindowsPhone.rest.connection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using Windows.Data.Xml.Dom;

namespace AFWindowsPhone.parsers
{
    interface XMLParser
    {
     
        AFSwinxConnectionPack parseDocument(XmlDocument documentToParse);
    }
}
