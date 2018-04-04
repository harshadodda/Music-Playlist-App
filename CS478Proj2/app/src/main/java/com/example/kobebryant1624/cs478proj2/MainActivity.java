package com.example.kobebryant1624.cs478proj2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    SongList sl;
    boolean webViewActive = false;
    ArrayAdapter<SongList.Song> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listView);
        sl = new SongList();
        lv.setLongClickable(true);

        adapter = new ArrayAdapter<SongList.Song> (this, android.R.layout.simple_list_item_2, android.R.id.text1, sl.songsList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);
                text1.setTextSize(25);
                text2.setTextSize(16);
                text2.setTypeface(null, Typeface.ITALIC);
                String title = sl.songsList.get(position).title;
                String artist = sl.songsList.get(position).artist;
                text1.setText(title);
                text2.setText(artist);
                return view;
            }
        };
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(sl.songsList.get(position).youtubeURL));
                webViewActive = true;
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(webViewActive) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SubMenu songsList = menu.addSubMenu("Remove");
        for(int i = 0; i < sl.songsList.size(); i++) {
            songsList.add(i, i, i, sl.songsList.get(i).title);
        }
        menu.add("Exit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String selected = item.getTitle().toString();
        switch(selected) {
            case "Add":
                addNewSong();
                break;
            case "Remove":
                break;
            case "Exit":
                finishAndRemoveTask();
                break;
            default:
                removeSong(item.getTitle().toString());
                break;
        }
        return true;
    }

    public void addNewSong() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_box);
        final EditText title = (EditText)dialog.findViewById(R.id.title);
        final EditText artist = (EditText)dialog.findViewById(R.id.artist);
        final EditText youtube = (EditText)dialog.findViewById(R.id.youtube);
        final EditText songWiki = (EditText)dialog.findViewById(R.id.songWikiBox);
        final EditText artistWiki = (EditText)dialog.findViewById(R.id.artistWikiBox);
        Button done = (Button)dialog.findViewById(R.id.doneButton);
        Button cancel = (Button)dialog.findViewById(R.id.cancelButton);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkTitle, checkArtist, checkYoutube, checkSongWiki, checkArtistWiki;

                checkTitle = title.getText().toString();
                checkArtist = artist.getText().toString();
                checkYoutube = youtube.getText().toString();
                checkSongWiki = songWiki.getText().toString();
                checkArtistWiki = artistWiki.getText().toString();

                if(checkTitle.isEmpty() || checkArtist.isEmpty() || checkYoutube.isEmpty()
                        || checkSongWiki.isEmpty() || checkArtistWiki.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "There was a mistake on text entry", Toast.LENGTH_SHORT).show();
                }
                else {
                    sl.addToList(checkTitle, checkArtist, checkYoutube, checkSongWiki, checkArtistWiki);
                    adapter.notifyDataSetChanged();
                    invalidateOptionsMenu();
                    dialog.hide();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();
    }

    public void removeSong(String title) {
        System.out.print(title);
        sl.remove(title);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Select An Option");
        menu.add(0, info.position, 0, "Youtube Video");
        menu.add(1, info.position, 1, "Song Wiki");
        menu.add(2, info.position, 2, "Artist Wiki");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getTitle().equals("Youtube Video")) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(sl.songsList.get(info.position).youtubeURL));
            startActivity(i);
        }
        else if(item.getTitle().equals("Song Wiki")) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(sl.songsList.get(info.position).songWikiURL));
            startActivity(i);
        }
        else if(item.getTitle().equals("Artist Wiki")) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(sl.songsList.get(info.position).artistWikiURL));
            startActivity(i);
        }
        else {
            return false;
        }
        return true;
    }


}
