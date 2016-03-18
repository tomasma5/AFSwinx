namespace AFWinPhone.rest.connection
{
    class RestBuilderFactory
    {
        private static RestBuilderFactory instance;

        private RestBuilderFactory()
        {

        }

        public static RestBuilderFactory getInstance()
        {
            if (instance == null)
            {
                instance = new RestBuilderFactory();
            }
            return instance;
        }

        /**
         * This method return builder based on content type of connection.
         * 
         * @param connection based on which will be returned builder.
         * @return Builder which can build data for request on server
         */
        public BaseRestBuilder getBuilder(AFSwinxConnection connection)
        {
            if (connection == null)
            {
                return new JSONBuilder();
            }
            if (connection.getContentType().Equals(HeaderType.JSON))
            {
                return new JSONBuilder();
            }
            else if (connection.getContentType().Equals(HeaderType.XML))
            {
                return new XMLBuilder();
            }
            else {
                return new JSONBuilder();
            }
        }
    }
}
