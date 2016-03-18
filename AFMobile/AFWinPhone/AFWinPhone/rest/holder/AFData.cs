﻿using System;

namespace AFWinPhone.rest.holder
{
    class AFData
    {
        // Its unique key in component structure
        private String key;
        private String value;

        public AFData(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        public String getKey()
        {
            return key;
        }

        public void setKey(String key)
        {
            this.key = key;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }
    }
}
