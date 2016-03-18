using AFWinPhone.rest.holder;

namespace AFWinPhone.rest.connection
{
    abstract class BaseRestBuilder : Reselization
    {
        public abstract object reselialize(AFDataHolder componentData);
    }
}
