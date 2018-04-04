package com.example.kobebryant1624.cs478proj2;

import java.util.ArrayList;

public class SongList {
    public ArrayList<Song> songsList = new ArrayList<Song>();

    public SongList() {
        this.addToList("Homecoming", "Kanye West", "https://www.youtube.com/watch?v=LQ488QrqGE4", "https://en.wikipedia.org/wiki/Homecoming_(Kanye_West_song)", "https://en.wikipedia.org/wiki/Kanye_West");
        this.addToList("Flashing Lights", "Kanye West", "https://www.youtube.com/watch?v=ila-hAUXR5U", "https://en.wikipedia.org/wiki/Flashing_Lights_(Kanye_West_song)", "https://en.wikipedia.org/wiki/Kanye_West");
        this.addToList("Style", "Taylor Swift", "https://www.youtube.com/watch?v=-CmadmM5cOk", "https://en.wikipedia.org/wiki/Style_(Taylor_Swift_song)", "https://en.wikipedia.org/wiki/Taylor_Swift");
        this.addToList("Money Trees", "Kendrick Lamar", "https://www.youtube.com/watch?v=NtxmnBQmfZs", "https://en.wikipedia.org/wiki/Money_Trees", "https://en.wikipedia.org/wiki/Kendrick_Lamar");
        this.addToList("HiiiPoWeR", "Kendrick Lamar", "https://www.youtube.com/watch?v=6xRerLLcJvY", "https://en.wikipedia.org/wiki/HiiiPoWeR", "https://en.wikipedia.org/wiki/Kendrick_Lamar");
    }

    public void addToList(String title, String artist, String youtubeURL, String songWikiURL, String artistWiki) {
        Song newSong = new Song();
        newSong.title = title;
        newSong.artist = artist;
        newSong.youtubeURL = youtubeURL;
        newSong.songWikiURL = songWikiURL;
        newSong.artistWikiURL = artistWiki;
        this.songsList.add(newSong);
    }

    public void remove(String title) {
        for(int i = 0; i < this.songsList.size(); i++) {
            if(this.songsList.get(i).title.equals(title)) {
                this.songsList.remove(this.songsList.get(i));
            }
        }
    }

    public class Song {
        String title;
        String artist;
        String youtubeURL;
        String songWikiURL;
        String artistWikiURL;
    }

}
