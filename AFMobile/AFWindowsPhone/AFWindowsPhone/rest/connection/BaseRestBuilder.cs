using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AFWindowsPhone.rest.holder;

namespace AFWindowsPhone.rest.connection
{
    abstract class BaseRestBuilder : Reselization
    {
        public abstract object reselialize(AFDataHolder componentData);
    }
}
