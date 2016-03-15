using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AFWindowsPhone.builders.components.types;
using Windows.UI.Xaml;
using AFWindowsPhone.enums;
using Windows.UI.Xaml.Controls;
using AFWindowsPhone.builders.components.parts;

namespace AFWindowsPhone.builders
{
    class FormBuilder : AFComponentBuilder<FormBuilder>
    {
        public override AFComponent createComponent()
        {
            this.initializeConnections();
            var modelTask = Task.Run(getModelResponse);
            modelTask.Wait();
            String modelResponse = modelTask.Result;

            AFForm form = (AFForm)this.buildComponent(modelResponse, SupportedComponents.FORM);

            var dataTask = Task.Run(getDataResponse);
            dataTask.Wait();
            String data = dataTask.Result;
            if (data != null)
            {
                form.insertData(data);
            }

            AFWindowsPhone.getInstance().addCreatedComponent(this.getComponentKeyName(), form);
            return form;
        }

        protected override FrameworkElement buildComponentView(AFComponent form)
        {
            StackPanel formView = new StackPanel();
            formView.Margin = new Thickness(getSkin().getComponentMarginLeft(), getSkin().getComponentMarginTop(),
                getSkin().getComponentMarginRight(), getSkin().getComponentMarginBottom());
    
            //set form layout orientation
            if (form.getLayoutOrientation().Equals(LayoutOrientation.AXISX))
            { //AXIS X
                formView.Orientation = Orientation.Vertical;
            }
            else { //AXIS Y
                formView.Orientation = Orientation.Horizontal;
            }

            //set fields layout orientation
            Orientation setOfFieldsOrientation;
            if (form.getLayoutOrientation().Equals(LayoutOrientation.AXISX))
            { //AXIS X
                setOfFieldsOrientation = Orientation.Horizontal;
            }
            else { //AXIS Y
                setOfFieldsOrientation = Orientation.Vertical;
            }

            //determine layout
            int numberOfColumns = form.getLayoutDefinitions().getNumberOfColumns();

            int i = 0;
            StackPanel setOfFields = null; ;
            foreach (AFField field in form.getFields())
            {
                if (!field.getFieldInfo().isVisible())
                {
                    continue;
                }
                if (i % numberOfColumns == 0)
                {
                    if (setOfFields != null)
                    {
                        formView.Children.Add(setOfFields);
                    }
                    setOfFields = new StackPanel();
                    setOfFields.Orientation = setOfFieldsOrientation;
                }

                FrameworkElement fieldView = field.getCompleteView();
                //TODO spread columns ion space equaly
                //fieldView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f / numberOfColumns));
                setOfFields.Children.Add(fieldView);

                if (i == form.getVisibleFieldsCount() - 1)
                {
                    formView.Children.Add(setOfFields);
                }

                i++;
            }

            return formView;
        }
    }
}
