﻿using System;

namespace AFWinPhone.rest.connection
{
    public class ConnectionSecurity
    {
        private SecurityMethod method = SecurityMethod.BASIC;
        private String userName;
        private String password;

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        public SecurityMethod getMethod()
        {
            return method;
        }

        public void setMethod(SecurityMethod method)
        {
            this.method = method;
        }
    }
}
