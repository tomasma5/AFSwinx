using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AFWindowsPhone.builders.components.types;
using Windows.UI.Xaml;
using AFWindowsPhone.enums;

namespace AFWindowsPhone.builders
{
    class FormBuilder : AFComponentBuilder<FormBuilder>
    {
        public override AFComponent createComponent()
        {
            this.initializeConnections();
            String modelResponse = this.getModelResponse();
            AFForm form = (AFForm)this.buildComponent(modelResponse, SupportedComponents.FORM);
            String data = this.getDataResponse();
            if (data != null)
            {
                form.insertData(data);
            }

            AFWindowsPhone.getInstance().addCreatedComponent(this.getComponentKeyName(), form);
            return form;
        }

        protected override FrameworkElement buildComponentView(AFComponent component)
        {
            throw new NotImplementedException();
        }
    }
}
