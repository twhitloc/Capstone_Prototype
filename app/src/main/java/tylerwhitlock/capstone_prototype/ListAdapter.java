package tylerwhitlock.capstone_prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tylerwhitlock on 3/27/16.
 */
public class ListAdapter extends ArrayAdapter<TranslatedPhrase> {
    private final Context context;
    private final ArrayList<TranslatedPhrase> list;

    public ListAdapter(Context context, ArrayList<TranslatedPhrase> itemList){
        super(context, R.layout.list_item, itemList);
        this.context = context;
        this.list = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView english = (TextView) rowView.findViewById(R.id.english_view);
        TextView asl = (TextView) rowView.findViewById(R.id.asl_view);

        english.setText("English : " + list.get(position).getEnglishPhrase());
        asl.setText(" ASL : " + list.get(position).getASLPhrase());

        return rowView;

    }


}
