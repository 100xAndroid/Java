package cz.stokratandroid.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PolozkaAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> skupiny, aktualniPolozka;
    private ArrayList<Object> polozky = new ArrayList<Object>();

    public PolozkaAdapter(Context context, ArrayList<String> groupList, ArrayList<Object> itemList) {
        this.context = context;
        skupiny = groupList;
        polozky = itemList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View customView, ViewGroup parent) {
        aktualniPolozka = (ArrayList<String>) polozky.get(groupPosition);

        // pokud dosud nebyl formular serializovan, serializovat
        if (customView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customView = inflater.inflate(R.layout.polozka, null);
        }

        // ziskat ze seznamu nazev
        String itemText = aktualniPolozka.get(childPosition);

        // nazev zapsat do textoveho pole
        TextView textField = (TextView) customView.findViewById(R.id.textView1);
        textField.setText(itemText);

        // vratit vytvorenou polozku
        return customView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) polozky.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return skupiny.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // pokud dosud nebyl formular serializovan, serializovat
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.skupina, null);
        }

        // ziskat ze seznamu nazev skupiny
        String textNazvuSkupiny = skupiny.get(groupPosition);

        // nazev zapsat do textoveho pole
        TextView nazevSkupiny = (TextView)convertView.findViewById(R.id.textView1);
        nazevSkupiny.setText(textNazvuSkupiny);

        // vratit vytvorene zahlavi skupiny
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}