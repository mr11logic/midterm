import sepo.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            User user1 = new User("user1", "password123");
            User user2 = new User("user2", "password456");
            User user3 = new User("user3", "password789");

            try {
                User duplicateUser = new User("user1", "password1011");
            } catch (InvalidOperationException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

            try {
                User shortPassUser = new User("user4", "short");
            } catch (InvalidOperationException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

            Music song1 = new Music("Mal mani");
            song1.setSinger(user3);
            Music.allMusic.add(song1);

            Music song2 = new Music("BAAQ");
            song2.setSinger(user3);
            Music.allMusic.add(song2);

            Music song3 = new Music("Lets dance");
            song3.setSinger(user1);
            Music.allMusic.add(song3);

            System.out.println("\nTesting Regular User Behavior:");
            user1.playMusic(song1);
            user1.playMusic(song2);
            user1.playMusic(song3);
            user1.playMusic(song1);
            user1.playMusic(song2);

            try {
                user1.playMusic(song3);
            } catch (InvalidOperationException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

            try {
                user1.creatPlayList("My Playlist", user1);
            } catch (InvalidOperationException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

            System.out.println("\nUpgrading user1 to premium:");
            user1.buyPremium(user1, 3);

            System.out.println("\nTesting Premium User Behavior:");
            user1.creatPlayList("My Favorites", user1);

            Playlist playlist = user1.getPlaylists().get(0);
            playlist.addMusic(song1, "password123");
            playlist.addMusic(song2, "password123");

            try {
                playlist.addMusic(song1, "password123");
            } catch (InvalidOperationException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

            try {
                playlist.addMusic(song3, "wpass123");
            } catch (InvalidOperationException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

            System.out.println("\nPlaying playlist:");
            playlist.playPlaylist();

            System.out.println("\nSearching for music:");
            ArrayList<Music> searchResults = song3.search("Lets dance");
            if (searchResults != null) {
                System.out.println("Found " + searchResults.size() + " songs with title 'Lets dance'");
            }

            System.out.println("\nTesting follow system:");
            user2.follow(user1);
            System.out.println(user1.getUsername() + " followers: " + user1.getFollowerList().size());
            System.out.println(user2.getUsername() + " following: " + user2.getFollowingList().size());

            System.out.println("\nFinal stats:");
            System.out.println("Total users: " + User.allUser.size());
            System.out.println("Total music: " + Music.allMusic.size());
            System.out.println(user1.getUsername() + " playlists: " + user1.getPlaylists().size());
            System.out.println("Streams for Mal mani: " + song1.getNumberOfStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}