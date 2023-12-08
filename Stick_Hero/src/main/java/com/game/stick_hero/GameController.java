package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.Block;
import com.game.stick_hero.StickHeroComponents.StickHero;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class GameController {
    @FXML
    private Label welcomeText;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    static StickHeroGame stickHeroGame;
    static StickHero stickHero;
    @FXML
    private AnchorPane ap;
    @FXML
       Label finalScore;
    @FXML
     Label bestScore;
    @FXML
    private Label name;
    @FXML
    private Label id;


    static int bestSc=0;
    int cnt=0;

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        //Stage stage=(Stage)ap.getScene().getWindow();
//        stickHero=(StickHeroGame) ap.getUserData();
//        System.out.println(stickHero.getName()+stickHero.getId());
//    }
    public  void receiveData(StickHero Hero) {
       // label.setText(myObject.getMessage());
        System.out.println(Hero.getName());

        stickHero=Hero;
    }
    @FXML
    public void playGame(ActionEvent e) throws URISyntaxException {
        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainGame.fxml"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        Random random = new Random();


        Block A=new Block(random.nextInt(30,150));
        stage.show();
        cnt=-1;
        stickHeroGame=new StickHeroGame(stickHero);
        stickHeroGame.startGame();
        play(stage,A);

    }
    @FXML
    public void goToMenu(ActionEvent e){
        FXMLLoader loader = new FXMLLoader(MultiplayerController.class.getResource("GameMenu.fxml"));
        Parent root2=null;
        try {
            root2= loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //userName.setText(name.getText());
        GameController controllerB = loader.getController();

        // Create an object to pass to ControllerB

        controllerB.receiveData(stickHero);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.show();
    }
    private void play(Stage stage, Block A) throws URISyntaxException {

         Random random = new Random();

            cnt++;
            stickHeroGame.setScore(cnt);
           // finalScore=new Label();
            System.out.println(cnt);
            Text score=new Text();
            score.setText("score: "+cnt);
            score.setX(200);
            score.setFont(Font.font("Verdana",30));
            score.setY(30);

            boolean isDone=false;
            Group root=new Group();
            scene=new Scene(root,600,400);
            Block B=new Block(random.nextInt(30,150));
            Rectangle rectangleA =new Rectangle();
            rectangleA.setX(5);
            rectangleA.setY(200);
            rectangleA.setWidth(A.getWidth());
            rectangleA.setHeight(200);
            Image image  = new Image(getClass().getResourceAsStream("stickHeroMan.png"));

          //  System.out.println(getClass().getResource("src/stickHeroMan.png"));
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            imageView.setX(A.getWidth()-25);
            imageView.setY(160);
           // imageView.setFitHeight(20);

            int distance=random.nextInt(10,200);
            Rectangle rectangleB =new Rectangle();
            rectangleB.setX(5+A.getWidth()+distance);
            rectangleB.setY(200);
            rectangleB.setWidth(B.getWidth());
            rectangleB.setHeight(200);
            root.getChildren().add(rectangleA);
            root.getChildren().add(rectangleB);
            root.getChildren().add(imageView);
        root.getChildren().add(score);
            stage.setScene(scene);
            stage.show();
            Line line=new Line();
            line.setStartX(A.getWidth()+1);
            line.setEndX(A.getWidth()+1);
            line.setStartY(200);
            line.setEndY(195);
            line.setStrokeWidth(5);
            root.getChildren().add(line);
            Rotate rotate =new Rotate();
            line.getTransforms().add(rotate);
            stickHeroGame.addScene(A,B,distance);

            scene.setOnKeyPressed(event->{
                if(!isDone && event.getCode()== KeyCode.SPACE){
                    line.setEndY(line.getEndY()-2);
                }
            });
            scene.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.SPACE) {
                    // Stop increasing the length of the line
                    //line.setEndY(line.getEndY());

                    rotate.setPivotX(line.getStartX());
                    rotate.setPivotY(line.getStartY());
                    rotate.setAngle(90);
//                    TranslateTransition translate=new TranslateTransition();
//                    translate.setNode(imageView);
                    double move=line.getStartY()-line.getEndY();
                    boolean isGameOver=false;
                    double finalPos=line.getStartX()+move;
                    if((finalPos<rectangleB.getX()) || (finalPos>(rectangleB.getX()+B.getWidth()))){
                        System.out.println("Game Over");
                        isGameOver=true;
                    }
                    else{
                        move=distance+B.getWidth();
                    }
                    if(isGameOver){
                        TranslateTransition translateX = new TranslateTransition(Duration.seconds(1), imageView);
                        translateX.setToX(move+6); // Translate in x direction by 50 pixels

                        TranslateTransition translateY = new TranslateTransition(Duration.millis(200), imageView);
                        translateY.setToY(200); // Translate in y direction by 30 pixels

                        // Play the translation animations sequentially
                        translateX.setOnFinished(event2 -> translateY.play());
                        translateY.setOnFinished(event2-> {
                            stage.setScene(createScene());

                          //  welcomeText.setText(cnt+"");
                        });
                        translateX.play();

//                        translate.setByX(move+6);
//                        translate.setDuration(Duration.millis(500));
                       // translate.play();
                       // stage.setScene(createScene());
                       // stage.show();

                    }
                    else {
                        TranslateTransition translateX = new TranslateTransition(Duration.seconds(2), imageView);
                        translateX.setToX(move+6);

                            translateX.setOnFinished(event2-> {
                                try {
                                    play(stage,B);
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            });


                        translateX.play();

                    }



                }
            });


    }

    private void setScore(int cnt) {
        finalScore.setText(cnt+"");
        int best=stickHero.getMaxScore();
        if(cnt>best){
           // bestSc=cnt;
            stickHero.setMaxScore(cnt);
            DataBaseService.updateScoreOfUser(stickHero.getId(),cnt);

        }
        bestScore.setText(stickHero.getMaxScore()+"");
    }

    private Scene createScene()  {
         Parent root= null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
        try {
           root=loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }



        GameController controllerB = loader.getController();

        // Create an object to pass to ControllerB

        controllerB.setScore(cnt);

        Scene scene=new Scene(root);

        //finalScore.setText(cnt+"");
        return scene;
    }
    @FXML
    public void showProfile(ActionEvent e){

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Create an object to pass to ControllerB
        Image image  = new Image(getClass().getResourceAsStream("profile.png"));
        ProfileController.showMyProfile(stage,stickHero,image);

    }
    @FXML
    public void compete(ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        MultiplayerController.showOtherPlayers(stage,stickHero);
    }
    @FXML
    private Label saveText;
    @FXML
    public void saveGame(){
        if(!stickHeroGame.isSaved()) {
            stickHeroGame.saveGame();

            saveText.setText("Saved Successfuly");
        }
        else{
            saveText.setText("Already Saved");
            saveText.setTextFill(Color.ALICEBLUE);
        }
    }
    public void showDetails(){
        name.setText(stickHero.getName());
        id.setText(stickHero.getId());
    }

}