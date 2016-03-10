using AFWindowsPhone.enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.builders.components.parts
{
    class LayoutProperties
    {
        private LayoutDefinitions layoutDefinition;
        private LayoutOrientation layoutOrientation;
        private LabelPosition labelPosition;

        public LayoutProperties()
        {
        }

        public LayoutDefinitions getLayoutDefinition()
        {
            return this.layoutDefinition;
        }

        public void setLayoutDefinition(LayoutDefinitions layoutDefinition)
        {
            this.layoutDefinition = layoutDefinition;
        }

        public LayoutOrientation getLayoutOrientation()
        {
            return this.layoutOrientation;
        }

        public void setLayoutOrientation(LayoutOrientation layoutOrientation)
        {
            this.layoutOrientation = layoutOrientation;
        }

        public LabelPosition getLabelPosition()
        {
            return this.labelPosition;
        }

        public void setLabelPosition(LabelPosition labelPosition)
        {
            this.labelPosition = labelPosition;
        }

    }
}
