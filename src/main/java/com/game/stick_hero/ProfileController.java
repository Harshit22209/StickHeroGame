package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.StickHero;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;


     static void showMyProfile(Stage stage,StickHero stickHero,Image image){
         Group root=new Group();
         ScrollPane scrollPane = new ScrollPane(root);

         Scene scene=new Scene(scrollPane,600,400);

         Text name=new Text();
         name.setText("Name:"+stickHero.getName());
         name.setX(100);
         name.setY(50);
         name.setFont(Font.font("Verdana",20));
         Text Id=new Text();

         Id.setText("Id:"+stickHero.getName());
         Id.setX(100);
         Id.setFont(Font.font("Verdana",20));
         Id.setY(80);

         ImageView imageView=new ImageView(image);
         imageView.setFitHeight(60);
         imageView.setPreserveRatio(true);
         imageView.setX(10);
         imageView.setY(30);
         Button back=new Button();
         back.setText("Back");
         back.setLayoutX(250);
         back.setLayoutY(30);
         back.setPrefSize(100, 40);
         back.setOnAction(actionEvent -> {

             FXMLLoader loader = new FXMLLoader(ProfileController.class.getResource("GameMenu.fxml"));
             Parent root2=null;
             try {
                root2= loader.load();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             //userName.setText(name.getText());
             GameController controllerB = loader.getController();

             // Create an object to pass to ControllerB

             controllerB.receiveStickHero(stickHero);

             // Stage stage = new Stage();
             stage.setScene(new Scene(root2));
             stage.show();
         });
        int y=150;
        for(var game:stickHero.getGamesPlayed()){
            System.out.println("Hello");
            Text score=new Text();
            score.setText(game.getScore()+"");
            score.setX(30);
            score.setY(y);

            score.setFont(Font.font("Verdana",20));
            y+=30;
            root.getChildren().add(score);
        }
        root.getChildren().addAll(name,Id,imageView,back);
        stage.setScene(scene);
    }

}
