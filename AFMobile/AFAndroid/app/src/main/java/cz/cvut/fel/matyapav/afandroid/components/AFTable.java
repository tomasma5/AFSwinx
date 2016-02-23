package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;

/**
 * Created by Pavel on 20.02.2016.
 */
public class AFTable extends AFComponent{

    private TableLayout contentLayout;
    private TableLayout headerLayout;
    private TableRow headerRow;
    private List<TableRow> rows;
    private int numberOfColumns;
    private ScrollView contentLayoutWrapper;

    public AFTable(Activity activity, AFSwinxConnection modelConnection, AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        super(activity, modelConnection, dataConnection, sendConnection);
    }

    public AFTable(String name, ViewGroup view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        super(name, view, layoutDefinitions, layoutOrientation);
    }

    @Override
    SupportedComponents getComponentType() {
        return SupportedComponents.TABLE;
    }

    @Override
    boolean validateData() {
        throw new UnsupportedOperationException("Table is read only");
    }

    @Override
    AFDataHolder reserialize() {
        throw new UnsupportedOperationException("Table is read only");
    }

    public TableLayout getContentLayout() {
        return contentLayout;
    }

    public void setContentLayout(TableLayout contentLayout) {
        this.contentLayout = contentLayout;
    }

    public TableLayout getHeaderLayout() {
        return headerLayout;
    }

    public void setHeaderLayout(TableLayout headerLayout) {
        this.headerLayout = headerLayout;
    }

    public List<TableRow> getRows() {
        return rows;
    }

    public void addRow(TableRow row){
        if(rows == null){
            rows = new ArrayList<>();
        }
        rows.add(row);
    }

    public void setHeaderRow(TableRow headerRow) {
        this.headerRow = headerRow;
    }

    public TableRow getHeaderRow() {
        return headerRow;
    }

    public View getCellAt(int row, int col){
        return getRows().get(row).getChildAt(col);
    }

    //graphic setters

    public void setHeaderCellBackgroundColor(int col, int color){
        View cell = getHeaderRow().getChildAt(col);
        if(cell != null){
            cell.setBackgroundColor(color);
        }
    }

    public void setHeaderCellTextColor(int col, int color){
        TextView cell = (TextView) getHeaderRow().getChildAt(col);
        if(cell != null){
            cell.setBackgroundColor(color);
        }
    }

    public void setCellBackgroundColor(int row, int col, int color){
        View cell = getCellAt(row, col);
        if(cell != null){
            cell.setBackgroundColor(color);
        }
    }

    public void setCellTextColor(int row, int col, int color){
        TextView cell = (TextView) getCellAt(row, col);
        if(cell != null){
            cell.setTextColor(color);
        }
    }

    public void setWidth(int width){
        ViewGroup.LayoutParams headerParams = getHeaderLayout().getLayoutParams();
        headerParams.width = width;
        getHeaderLayout().setLayoutParams(headerParams);

        ViewGroup.LayoutParams contentWrapperParams = getContentLayoutWrapper().getLayoutParams();
        contentWrapperParams.width = width;
        getContentLayoutWrapper().setLayoutParams(contentWrapperParams);
    }

    public void setContentLayoutWrapper(ScrollView contentLayoutWrapper) {
        this.contentLayoutWrapper = contentLayoutWrapper;
    }

    public ScrollView getContentLayoutWrapper() {
        return contentLayoutWrapper;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
}
