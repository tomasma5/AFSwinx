package com.tomscz.afswinx.component.widget.builder.abstraction;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.tomscz.afrest.layout.Layout;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;
import com.tomscz.afswinx.component.widget.builder.LayoutBuilder;

/**
 * This is base layout builder which can build layout for concrete {@link Layout}. It can build
 * layout for field or for class as well.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class BaseLayoutBuilder implements LayoutBuilder {

    protected int numberOfComponentsInAxis;
    protected int layoutOrientation;
    protected LabelPosition labelPosition;
    protected JTextArea label;
    protected JComponent message;
    protected int numberOfComponentInActualPanel;
    protected List<JComponent> components = new ArrayList<JComponent>();

    public BaseLayoutBuilder(Layout layout) {
        this.initBuilder(layout);
    }

    /**
     * This method initialize builder. Builder will be set to default after initialization the
     * settings will be concrete by layout if exist in this particular field info.
     * 
     * @param layout based on will be builder initialize
     */
    private void initBuilder(Layout layout) {
        // First set default values in case if layout is not specify
        this.numberOfComponentsInAxis = Integer.MAX_VALUE;
        this.layoutOrientation = BoxLayout.Y_AXIS;
        this.labelPosition = LabelPosition.BEFORE;
        // If layout is specify, then used it
        if (layout != null) {
            // Get data, if some of them is null, then dont set which means that default value will
            // be used
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
        if (component != null) {
            this.components.add(component);
        }
    }

    @Override
    public void addLabel(JTextArea label) {
        if (label != null) this.label = label;
    }

    /**
     * This method build layout to existed {@link JPanel}
     * 
     * @param outputPanel current swing panel to which will be added components
     */
    @Override
    public void buildLayout(JPanel outputPanel) {
        // Based on label position determine if label will be before or after field, but only if
        // label was specified
        if (label != null) {
            if (this.labelPosition == LabelPosition.AFTER) {
                this.addComponent(label);
            } else {
                components.add(0, label);
            }
        }
        JPanel actualPanel = null;
        List<JPanel> panels = new ArrayList<JPanel>();
        for (JComponent component : components) {
            if (actualPanel == null) {
                actualPanel = new JPanel();
                BoxLayout layout = new BoxLayout(actualPanel, layoutOrientation);
                actualPanel.setLayout(layout);
                actualPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                actualPanel.add(Box.createGlue());
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
        JPanel panelWhichHoldPanels = outputPanel;
        GridLayout gridLayout;
        if (layoutOrientation == BoxLayout.X_AXIS) {
            gridLayout = new GridLayout(panels.size(), 0, 0, 0);
        } else {
            gridLayout = new GridLayout(0, panels.size(), 0, 0);
        }
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
            outputPanel.setLayout(new GridBagLayout());
            GridBagConstraints c1 = new GridBagConstraints();
            c1.fill = GridBagConstraints.BOTH;
            c1.gridx = 0;
            c1.gridy = 0;
            outputPanel.add(message, c1);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1.0;
            c.gridx = 0;
            c.gridy = 1;
            outputPanel.add(panelWhichHoldPanels, c);
            outputPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        }
    }

    @Override
    public void addMessage(JComponent messageComponent) {
        if (messageComponent != null) this.message = messageComponent;
    }

}
