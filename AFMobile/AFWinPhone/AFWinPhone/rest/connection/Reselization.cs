using AFWinPhone.rest.holder;


namespace AFWinPhone.rest.connection
{
    /**
     * This interface specify method, which are used to rebuild data and these data will be send back to
     * server or this data comes from server and must be interpreted. This interface provide only method
     * which leads to data. No connection or request is performed there.
     * 
     * @author Martin Tomasek (martin@toms-cz.com)
     * 
     * @since 1.0.0.
     */
    interface Reselization
    {
        /**
        * This method build data which will be set to server. Data are build from componentData
        * parameter in this method.
        * 
        * @param componentData data which will be used to build data which will be send on server.
        * @return Data which will be send on server.
        */
        object reselialize(AFDataHolder componentData);

    }
}
