using AFWinPhone.enums;

namespace AFWinPhone.components.parts
{
    public class LayoutProperties
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
