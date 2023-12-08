package com.game.stick_hero;
import com.game.stick_hero.StickHeroComponents.Block;
import com.game.stick_hero.StickHeroComponents.GameDetail;
import com.game.stick_hero.StickHeroComponents.StickHero;
import com.game.stick_hero.StickHeroComponents.StickHeroScene;
import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;

import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DataBaseService {
    static MongoDatabase db;
    static MongoCollection<Document> users;
    static MongoCollection<Document> games;
    static MongoClient mongoClient;
    static void connect(){
        String connectionString =  "mongodb+srv://admin-harshit:ha2744@cluster0.4rsqkc5.mongodb.net/?retryWrites=true&w=majority";
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
        if(user==null){
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
                .append("bestScore",0));
    }
    static void addGame(String id,ArrayList<StickHeroScene> scenes){
        String game="";
        for(StickHeroScene scene:scenes){
            game+=scene+" ";
        }
        games.insertOne(new Document()
                .append("id",id)
                .append("Scenes",game.strip()));

    }
    static void addGameToUser(String UserId,String GameId,int score){
        Document filter=new Document("id",UserId);
        Document user= users.find(eq("id",UserId)).first();
        List<String> gamesPlayed=  user.getList("GamesPlayed",String.class);
        gamesPlayed.add(score+"#"+GameId);
        Document update=new Document("$set",new Document("GamesPlayed",gamesPlayed));
        users.updateOne(filter,update);
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
            ArrayList<StickHeroScene> scenesList = new ArrayList<>();
            for (String scene : Scenes) {
                String[] Elements = scene.split(":");
                int wa = Integer.parseInt(Elements[0]);
                int wb = Integer.parseInt(Elements[1]);
                int d = Integer.parseInt(Elements[2]);
                scenesList.add(new StickHeroScene(new Block(wa), new Block(wb), d));

            }
        }
        return new StickHero(user.getString("name"),user.getString("id"),user.getInteger("bestScore"),gameList);
    }
}
