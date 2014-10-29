package com.tomscz.afswinx.unmarshal.builders.abstraction.layout;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tomscz.afrest.layout.Layout;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.unmarshal.builders.LayoutBuilder;

/**
 * This is base layout builder which can build layout for concrete {@link AFFieldInfo} which holds
 * fieldInfo
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class BaseLayoutBuilder implements LayoutBuilder {

    private int numberOfComponentsInAxis;
    private int layoutOrientation;
    private LabelPosition labelPosition;
    private JLabel label;
    private JComponent message;
    private int numberOfComponentInActualPanel;
    List<JComponent> components = new ArrayList<JComponent>();

    public BaseLayoutBuilder(AFFieldInfo fieldInfo) {
        this.initBuilder(fieldInfo);
    }

    /**
     * This method initialize builder. Builder will be set to default after initialization the
     * settings will be concrete by layout if exist in this particular field info.
     * 
     * @param fieldInfo based on will be builder initialize, important part is layout
     */
    private void initBuilder(AFFieldInfo fieldInfo) {
        // First set default values in case if fieldInfo is not specify
        this.numberOfComponentsInAxis = Integer.MAX_VALUE;
        this.layoutOrientation = BoxLayout.Y_AXIS;
        this.labelPosition = LabelPosition.BEFORE;
        // If layout is specify, then used it
        if (fieldInfo != null && fieldInfo.getLayout() != null) {
            // Get data, if some of them is null, then dont set which means that default value will
            // be used
            Layout layout = fieldInfo.getLayout();
            LayoutOrientation layoutOrientation = layout.getLayoutOrientation();
            LabelPosition labelPosstion = layout.getLabelPosstion();
            LayouDefinitions layoutDefinition = layout.getLayoutDefinition();
            if (labelPosstion != null) {
                this.labelPosition = labelPosstion;
            }
            if (layoutDefinition != null) {
                if (layoutDefinition == LayouDefinitions.ONECOLUMNLAYOUT) {
                    this.numberOfComponentsInAxis = 1;
                } else if (layoutDefinition == LayouDefinitions.TWOCOLUMNSLAYOUT) {
                    this.numberOfComponentsInAxis = 2;
                }
            }
            if (layoutOrientation != null) {
                if (layoutOrientation == LayoutOrientation.AXISX) {
                    this.layoutOrientation = BoxLayout.X_AXIS;
                } else {
                    this.layoutOrientation = BoxLayout.Y_AXIS;
                }
            }
        }
    }

    @Override
    public void addComponent(JComponent component) {
        if (component != null) this.components.add(component);
    }

    @Override
    public void addLabel(JLabel label) {
        if (label != null) this.label = label;
    }

    /**
     * This method build layout to existed {@link AFSwinxPanel}
     * 
     * @param swingPanel current swing panel to which will be added components
     */
    @Override
    public void buildLayout(AFSwinxPanel swingPanel) {
        // Based on label position determine if label will be before or after field
        if (this.labelPosition == LabelPosition.AFTER) {
            this.addComponent(label);
        } else {
            components.add(0, label);
        }
        JPanel actualPanel = null;
        List<JPanel> panels = new ArrayList<JPanel>();
        for (JComponent component : components) {
            if (actualPanel == null) {
                actualPanel = new JPanel();
                BoxLayout layout = new BoxLayout(actualPanel, layoutOrientation);
                actualPanel.setLayout(layout);
            }
            actualPanel.add(component);
            numberOfComponentInActualPanel++;
            if (numberOfComponentInActualPanel >= numberOfComponentsInAxis) {
                panels.add(actualPanel);
                numberOfComponentInActualPanel = 0;
                actualPanel = null;
            }
        }
        if (actualPanel != null) {
            panels.add(actualPanel);
        }
        // Put components together and add message field if should be added
        JPanel panelWhichHoldPanels = swingPanel;
        GridLayout gridLayout = new GridLayout(panels.size(), 1);
        // if there is message to add, then wrapper should be used around swing panel
        if (message != null) {
            panelWhichHoldPanels = new JPanel();
        }
        panelWhichHoldPanels.setLayout(gridLayout);
        // Add component to panel
        for (JPanel panel : panels) {
            panelWhichHoldPanels.add(panel);
        }
        // If wrapper was used then set it to swing panel with message
        if (message != null) {
            GridLayout mainGridLayout = new GridLayout(1, 2);
            swingPanel.setLayout(mainGridLayout);
            swingPanel.add(panelWhichHoldPanels);
            swingPanel.add(message);
        }
    }

    @Override
    public void addMessage(JComponent messageComponent) {
        if (messageComponent != null) this.message = messageComponent;
    }
}
