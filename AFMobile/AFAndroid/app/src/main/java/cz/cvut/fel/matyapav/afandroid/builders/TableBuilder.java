package cz.cvut.fel.matyapav.afandroid.builders;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.AFTable;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;


/**
 * Created by Pavel on 20.02.2016.
 */
public class TableBuilder extends AFComponentBuilder<TableBuilder> {
    //table properties
    private static int DEFAULT_TABLE_HEIGHT = 650;
    private static int DEFAULT_HEADER_ROW_HEIGHT = 100; //with padding
    private static int DEFAULT_CONTENT_ROW_HEIGHT = 150;
    private static int DEFAULT_TABLE_WIDTH = 1200;

    //colors
    private static int DEFAULT_HEADER_COLOR = Color.GRAY;
    private static int DEFAULT_CONTENT_COLOR = Color.LTGRAY;

    //cell properties
    private static final int DEFAULT_PADDING = 20;
    private static int DEFAULT_HEADER_CELL_GRAVITY = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
    private static int DEFAULT_CONTENT_CELL_GRAVITY = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
    private static final int DEFAULT_BORDER_WIDTH = 3 ;



    @Override
    public AFTable createComponent() throws Exception {
        initializeConnections();
        String modelResponse = getModelResponse();
        //create form from response
        AFTable table = (AFTable) buildComponent(modelResponse, SupportedComponents.TABLE);
        //fill it with data (if there are some)
        String data = getDataResponse();
        if(data != null) {
            insertData(data, table);
        }
        AFAndroid.getInstance().addCreatedComponent(getComponentKeyName(), table);
        return table;
    }

    private void insertData(String dataResponse, AFComponent table){
        insertData(dataResponse, table, null); //road is not needed in this case
    }

    @Override
    protected void insertData(String dataResponse, AFComponent table, StringBuilder road) {
        List<String> longestRowList = new ArrayList<>();
        int longestRowLength = 0;
        try {
            JSONArray jsonArray = new JSONArray(dataResponse);
            for(int i=0; i<jsonArray.length(); i++){
                TableRow row = new TableRow(getActivity());
                row.setClickable(true);
                row.setFocusable(true);

                //list of strings within one row - used to determine longest row
                List<String> rowList = new ArrayList<>();
                int rowLength = 0;

                //iterate over columns
                Iterator<String> recordKeys = ((JSONObject) jsonArray.get(i)).keys();
                while(recordKeys.hasNext()) {
                    String column = recordKeys.next();
                    if(shouldBeInvisible(column, table)){
                        continue; //do not bother with invisible columns
                    }
                    //build cell
                    TextView cell = new TextView(getActivity());
                    setCellParams(cell, DEFAULT_CONTENT_CELL_GRAVITY, DEFAULT_PADDING, DEFAULT_BORDER_WIDTH);
                    String value = Localization.translate(((JSONObject) jsonArray.get(i)).get(column).toString(), getActivity());
                    cell.setText(value);
                    row.addView(cell, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, DEFAULT_CONTENT_ROW_HEIGHT));

                    //add value in column to row list
                    rowList.add(value);
                    //increase length of row
                    rowLength += value.length();
                }
                ((AFTable) table).getContentLayout().addView(row, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)); //add row to table
                ((AFTable) table).addRow(row);
                //set longest row
                if(rowLength > longestRowLength){
                    longestRowList = rowList;
                    longestRowLength = rowLength;
                }

            }

            //add longest row to header as fake row with 0 height to have all columns properly sized
            TableRow fakeHeaderRow = new TableRow(getActivity());
            for (String column: longestRowList) {
                TextView columnView = new TextView(getActivity());
                setCellParams(columnView, DEFAULT_HEADER_CELL_GRAVITY, DEFAULT_PADDING, DEFAULT_BORDER_WIDTH);
                columnView.setText(column);
                fakeHeaderRow.addView(columnView, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 0));
            }
            ((AFTable) table).getHeaderLayout().addView(fakeHeaderRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        } catch (JSONException e) {
            //TODO better exception handling
            System.err.println("CANNOT PARSE DATA");
            e.printStackTrace();
        }
    }

    @Override
    protected View buildComponentView(AFComponent component) {
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());
        horizontalScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DEFAULT_TABLE_HEIGHT));
        horizontalScrollView.setScrollbarFadingEnabled(false);
        //setup wrapper for table
        LinearLayout tableWrapper = new LinearLayout(getActivity());
        tableWrapper.setOrientation(LinearLayout.VERTICAL);
        tableWrapper.setLayoutParams(new ViewGroup.LayoutParams(DEFAULT_TABLE_WIDTH, LinearLayout.LayoutParams.WRAP_CONTENT));

        //setup table layout for header row
        TableLayout headerTableLayout = new TableLayout(getActivity());
        headerTableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, DEFAULT_HEADER_ROW_HEIGHT));
        headerTableLayout.setStretchAllColumns(true);
        headerTableLayout.setShrinkAllColumns(true);

        //setup vertical scroll view for table content
        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_HEIGHT-DEFAULT_HEADER_ROW_HEIGHT));
        scrollView.setScrollbarFadingEnabled(false);

        //setup table layout for table content
        TableLayout contentTableLayout = new TableLayout(getActivity());
        contentTableLayout.setBackgroundColor(DEFAULT_CONTENT_COLOR);
        contentTableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        contentTableLayout.setStretchAllColumns(true);
        contentTableLayout.setShrinkAllColumns(true);

        TableRow headerRow = new TableRow(getActivity());
        headerRow.setBackgroundColor(DEFAULT_HEADER_COLOR);

        TableRow fakeContentRow = new TableRow(getActivity());

        //insert to header row
        int numberOfColumns = 0;
        for (AFField field : component.getFields()) {
            if (!field.getFieldInfo().isVisible()) {
                continue;
            }
            numberOfColumns++;

            TextView columnHeaderText = new TextView(getActivity());
            setCellParams(columnHeaderText, DEFAULT_HEADER_CELL_GRAVITY, DEFAULT_PADDING, DEFAULT_BORDER_WIDTH);
            columnHeaderText.setText(Localization.translate(field.getFieldInfo().getLabel(), getActivity()));
            headerRow.addView(columnHeaderText, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, DEFAULT_HEADER_ROW_HEIGHT));
            ((AFTable) component).setHeaderRow(headerRow);

            //create fake header row which will be added to content table to align items according to header row
            TextView fakeColumnHeaderText = new TextView(getActivity());
            setCellParams(fakeColumnHeaderText, DEFAULT_CONTENT_CELL_GRAVITY, DEFAULT_PADDING, DEFAULT_BORDER_WIDTH);
            fakeColumnHeaderText.setText(Localization.translate(field.getFieldInfo().getLabel(), getActivity()));
            fakeContentRow.addView(fakeColumnHeaderText, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 0));
        }
        //set numberOfColumns
        ((AFTable) component).setNumberOfColumns(numberOfColumns);

        //add header row to header table and assign header layout to table (to be able to get it later)
        headerTableLayout.addView(headerRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));
        ((AFTable) component).setHeaderLayout(headerTableLayout);


        //add fake header row to content table and assign content layout to table (to be able to get it later)
        contentTableLayout.addView(fakeContentRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        ((AFTable) component).setContentLayout(contentTableLayout);

        scrollView.addView(contentTableLayout);
        ((AFTable) component).setContentLayoutWrapper(scrollView);
        tableWrapper.addView(headerTableLayout);
        tableWrapper.addView(scrollView);

        horizontalScrollView.addView(tableWrapper);
        return horizontalScrollView;
    }
    private void setCellParams(TextView cell, int gravity, int paddingLeft, int paddingRight, int paddingTop, int paddingBottom, int borderWidth){
        //create border
        ShapeDrawable rect = new ShapeDrawable(new RectShape());
        rect.getPaint().setStyle(Paint.Style.STROKE);
        rect.getPaint().setStrokeWidth(borderWidth);

        cell.setGravity(gravity);
        cell.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        cell.setBackground(rect);
    }

    private void setCellParams(TextView cell, int gravity, int padding, int borderWidth){
       setCellParams(cell, gravity, padding, padding, padding, padding, borderWidth);
    }


    private boolean shouldBeInvisible(String column, AFComponent component) {
        for(AFField field: component.getFields()){
            if(field.getId().equals(column)){
                return !field.getFieldInfo().isVisible();
            }
        }
        return true;
    }

}
