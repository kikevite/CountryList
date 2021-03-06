package edu.upc.eseiaat.pma.countrylist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {

    private ArrayList<String> country_list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("country_list", country_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        final String[] countries = getResources().getStringArray(R.array.countries);

        if (savedInstanceState==null){country_list = new ArrayList<>(Arrays.asList(countries));}
        else {country_list=savedInstanceState.getStringArrayList("country_list");}

        ListView list = (ListView) findViewById(R.id.country_list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country_list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {
                Toast.makeText(CountryListActivity.this, String.format("Has clicat '%s'.", country_list.get(pos)), Toast.LENGTH_SHORT).show();

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                builder.setTitle(R.string.confirm);
                String msg = getResources().getString(R.string.confirm_message);
                builder.setMessage(msg + country_list.get(pos) + "?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        country_list.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton(android.R.string.no, null);
                builder.create().show();
                return true;
            }
        });
    }
}
