using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.rest.holder
{
    class AFDataPack
    {
        private List<AFData> data;
        private String className;

        public AFDataPack(String className)
        {
            this.className = className;
        }

        public AFDataPack(String className, List<AFData> data)
        {
            this.className = className;
            this.data = data;
        }

        public List<AFData> getData()
        {
            return data;
        }

        public void setData(List<AFData> data)
        {
            this.data = data;
        }

        public void addData(AFData data)
        {
            if (this.data == null)
            {
                this.data = new List<AFData>();
            }
            this.data.Add(data);
        }

        public String getClassName()
        {
            return className;
        }

        public void setClassName(String className)
        {
            this.className = className;
        }

    }
}
