package com.example.musicplayer;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    public static ArrayList<MusicDto> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 디바이스 안에 있는 mp3 파일 리스트를 조회 후 리스트 생성
        getMusicList();
        listView = (ListView)findViewById(R.id.listview);
        MyAdapter adapter = new MyAdapter(this,list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,MusicActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("playlist",list);
                startActivity(intent);
            }
        });
    }


    public  void getMusicList(){
        list = new ArrayList<>();
        //가져오고 싶은 컬럼 명을 나열. (음악의 id, 앰블럼 id, 제목, 아스티스트 정보를 get)
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        while(cursor.moveToNext()){
            MusicDto musicDto = new MusicDto();
            musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDto.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            list.add(musicDto);
        }
        cursor.close();
    }


}
