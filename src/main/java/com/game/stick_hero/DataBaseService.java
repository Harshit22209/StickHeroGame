package com.game.stick_hero;
import com.game.stick_hero.StickHeroComponents.*;
import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataBaseService {
    static MongoDatabase db;
    static MongoCollection<Document> users;
    static MongoCollection<Document> games;
    static MongoClient mongoClient;
    static void connect(){
        String connectionString =  "mongodb+srv://admin-harshit:kSwEu1E8GbUNWVf2@cluster0.4rsqkc5.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "stickHeroGame";

        try  {
            mongoClient = MongoClients.create(connectionString);
            db = mongoClient.getDatabase(databaseName);

            users=db.getCollection("Users");
            games=db.getCollection("Games");
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static StickHero getMainUser(String id,String name,String password) throws UserNotFoundException {
        //connect();

        Document user= users.find(eq("id",id)).first();
     //   System.out.println("hello");
        if(user==null){
            //System.out.println("hello");
             return null;
        }
        if(!name.equals(user.getString("name")) || !password.equals(user.getString("password")) ){
            throw new UserNotFoundException("Invalid Credential");
        }

        return convertToStichHero(user);


    }
    static void addUser(String name,String Id, String Password){
        List li=new ArrayList<>();
        users.insertOne(new Document()
                .append("name",name)
                .append("id",Id)
                .append("password",Password)
                .append("GamesPlayed",li)
                .append("bestScore",0)
                .append("cherries",0));
    }
    static void addGame(String id,ArrayList<StickHeroScene> scenes,int bestScore){
        String game="";
        for(StickHeroScene scene:scenes){
            game+=scene+" ";
        }
        games.insertOne(new Document()
                .append("id",id)
                .append("Scenes",game.strip())
                .append("bestScore",bestScore)
                .append("players",new ArrayList<>()));



    }
    static void addGameToUser(String UserId,String GameId,int score){
        //Adding game to user
        Document filter=new Document("id",UserId);
        Document user= users.find(eq("id",UserId)).first();
        List<String> gamesPlayed=  user.getList("GamesPlayed",String.class);
        gamesPlayed.add(score+"#"+GameId);
        Document update=new Document("$set",new Document("GamesPlayed",gamesPlayed));
        users.updateOne(filter,update);
        //Adding user to game
        Document game=games.find(eq("id",GameId)).first();
        filter=new Document("id",GameId);
        List<String> playersPlayed=game.getList("players",String.class);
        playersPlayed.add(UserId+"#"+user.getString("name")+"#"+score);
         update=new Document("$set",new Document("players",playersPlayed));
        games.updateOne(filter,update);

    }
    static String createId(){
        return (games.countDocuments()+1)+"";
    }
    static ArrayList<StickHero> getAllPlayers(){
        FindIterable<Document> documents = users.find().sort(Sorts.descending("bestScore"));
        ArrayList<StickHero> heros=new ArrayList<>();
        for(Document user:documents){
            heros.add(convertToStichHero(user));
        }
        return heros;

    }
    static void updateScoreOfUser(String id,int bestScore){
        Document filter=new Document("id",id);
//        Document user= users.find(eq("id",id)).first();
        Document update=new Document("$set",new Document("bestScore",bestScore));
        users.updateOne(filter,update);
    }
    static void checkAndUpdateScoreOfUser(String id,int score){
        Document user= users.find(eq("id",id)).first();
        if(score>user.getInteger("bestScore")) {
            updateScoreOfUser(id,score);
        }

    }
    static void updateCherriesOfUser(String id,int cherries){
        Document filter=new Document("id",id);
//        Document user= users.find(eq("id",id)).first();
        Document update=new Document("$set",new Document("cherries",cherries));
        users.updateOne(filter,update);
    }
    private static StickHero convertToStichHero(Document user) {
        List<String> gamesPlayed = user.getList("GamesPlayed", String.class);
        ArrayList<GameDetail> gameList = new ArrayList<>();
        System.out.println(gamesPlayed.size());
        for (var gameInfo : gamesPlayed) {
            String[] gameDetail = gameInfo.split("#");
            int score = Integer.parseInt(gameDetail[0]);
            String gameId = gameDetail[1];
            Document game = games.find(eq("id", gameId)).first();
            String[] Scenes = game.getString("Scenes").split(" ");
            int bestScore=game.getInteger("bestScore");
            ArrayList<StickHeroScene> scenesList = new ArrayList<>();
            for (String scene : Scenes) {
                String[] Elements = scene.split(":");
                int wa = Integer.parseInt(Elements[0]);
                int wb = Integer.parseInt(Elements[1]);
                int d = Integer.parseInt(Elements[2]);
                int cherryPos=Integer.parseInt(Elements[3]);
                scenesList.add(new StickHeroScene(new Block(wa), new Block(wb), d,new Cherry(cherryPos)));

            }
            gameList.add(new GameDetail(score,scenesList,bestScore,gameId));
        }
        return new StickHero(user.getString("name"),user.getString("id"),user.getInteger("bestScore"),gameList,user.getInteger("cherries"));
    }

    public static ArrayList<String[]> getAllPlayersPlayedGame(String gameId) {
        Document game= games.find(eq("id",gameId)).first();
        List<String> playerIds=game.getList("players",String.class);
        ArrayList<String[]> players=new ArrayList<>();
        for(var player:playerIds) players.add(player.split("#"));
        Collections.sort(players, Comparator.comparingInt(p -> (-1)*Integer.parseInt(p[2])));
        return players;
    }

    public static void updateGame(GameDetail gameInfo) {
        Document game= games.find(eq("id",gameInfo.getId())).first();
        String scenes="";
        for(StickHeroScene scene:gameInfo.getScenes()){
            scenes+=scene+" ";
        }
        Document filter=new Document("id",gameInfo.getId());
//        Document user= users.find(eq("id",id)).first();
        Document update=new Document("$set",new Document("Scenes",scenes));
        games.updateOne(filter,update);

    }
}
