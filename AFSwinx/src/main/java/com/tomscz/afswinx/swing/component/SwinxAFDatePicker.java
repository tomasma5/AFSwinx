package com.tomscz.afswinx.swing.component;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.DateComponentFormatter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 * This class represent date picker. It use {@link JDatePicker}. However if field is read only then
 * is used our own formatted {@link JFormattedTextField} which display data in form.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class SwinxAFDatePicker extends JComponent {

    private static final long serialVersionUID = 1L;

    private JDatePickerImpl datePicker;
    private JFormattedTextField textField;
    private boolean isReadOnly = false;

    public SwinxAFDatePicker() {
        // Build DatePicker
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        this.datePicker = new JDatePickerImpl(datePanel);
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
        if (this.isReadOnly) {
            textField = new JFormattedTextField(new DateComponentFormatter());
            textField.setEnabled(!isReadOnly);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        // If enable is set then set component readonly too if enable = true then readonly = false
        setReadOnly(!enabled);
        super.setEnabled(enabled);
    }

    /**
     * This method return component which will be visible
     * 
     * @return component which will be visible
     */
    public JComponent getVisibleComponent() {
        if (isReadOnly) {
            return textField;
        }
        return datePicker;
    }

    /**
     * This method return text field which hold data.
     * 
     * @return textFieldWhich holdData
     */
    public JFormattedTextField getVisibleTextField() {
        if (isReadOnly) {
            return textField;
        }
        return datePicker.getJFormattedTextField();
    }

}
