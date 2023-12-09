package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
//Used for Template Design Pattern
public abstract class GamePlay {
    private StickHeroGame stickHeroGame;
    private int cnt;
    private Scene scene;
     private Stage stage;
    private ImageView cherryImage;
    private Line line;
    private Group root;
    private AtomicBoolean isForFlip;
    private AtomicBoolean isDone = new AtomicBoolean(false);
    private StickHero stickHero;
    private Rotate rotate;
    private  Rectangle rectangleA;
    private  Rectangle rectangleB;
    private  ImageView imageView;
    private Rectangle mid;
    private StickHeroScene sceneComponent;
    private AtomicLong currentTimeMillis;

    public StickHeroGame getStickHeroGame() {
        return stickHeroGame;
    }

    public void setStickHeroGame(StickHeroGame stickHeroGame) {
        this.stickHeroGame = stickHeroGame;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public StickHero getStickHero() {
        return stickHero;
    }

    public void setStickHero(StickHero stickHero) {
        this.stickHero = stickHero;
    }

    public StickHeroScene getSceneComponent() {
        return sceneComponent;
    }

    public void setSceneComponent(StickHeroScene sceneComponent) {
        this.sceneComponent = sceneComponent;
    }

    public String getImageInput() {
        return imageInput;
    }

    public void setImageInput(String imageInput) {
        this.imageInput = imageInput;
    }

    private AtomicReference<Double> velocity;
    private AtomicReference<TranslateTransition> translate;
    private AtomicBoolean isCherryCollected;
    private AtomicBoolean gameOverForThread;
    private String imageInput="";
    private MusicService musicService=MusicService.getInstance();

    private int incY=200;

    void setScene() {
        cnt++;
        stickHeroGame.setScore(cnt);
        System.out.println(cnt);

        //Fetching All the scene Data
        root = new Group();
        scene = new Scene(root, 600, 600);
        Block A = sceneComponent.getA();
        Block B = sceneComponent.getB();
        int distance = sceneComponent.getDistance();
        Cherry cherry = sceneComponent.getCherry();
        stickHeroGame.addScene(A,B,distance,cherry);


        // Setting up background image
        Image backgroundImage = new Image(getClass().getResourceAsStream(imageInput)); // Replace with the path to your image
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(stage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stage.heightProperty());
        ImageView cherryCountImage=(ImageView) Components.getComponent(Components.Componenet.ImageView, 350, 8, "cherry.png", 30, 0);
        Text cherryCount = (Text) Components.getComponent(Components.Componenet.Text, 390, 30, ": " +stickHeroGame.getCherryCount(), 30, 0);

        root.getChildren().addAll(backgroundImageView,cherryCount,cherryCountImage);
        //Creating Scene
        Text score = (Text) Components.getComponent(Components.Componenet.Text, 200, 30, "score: " + cnt, 30, 0);
        rectangleA = (Rectangle) Components.getComponent(Components.Componenet.Rectangle, 5, 200+incY, "", 200, A.getWidth());
        rectangleB = (Rectangle) Components.getComponent(Components.Componenet.Rectangle, 5 + A.getWidth() + distance, 200+incY, "", 200, B.getWidth());
        mid=(Rectangle) Components.getComponent(Components.Componenet.Rectangle, 5 + A.getWidth() + distance+(B.getWidth()/2-4), 199+incY, "", 1, 8);
        mid.setFill(Color.RED);
        imageView = (ImageView) Components.getComponent(Components.Componenet.ImageView, A.getWidth() - 25, 160+incY, "stickHeroMan.png", 40, 0);
        imageView.setPreserveRatio(true);
        line = (Line) Components.getComponent(Components.Componenet.Line, A.getWidth() + 1, 200+incY, "", A.getWidth() + 1, 195+incY);
        line.setStrokeWidth(5);
        root.getChildren().addAll(rectangleA, rectangleB, imageView, score, line, mid);
        stage.setScene(scene);
        stage.show();
        rotate = new Rotate();
        line.getTransforms().add(rotate);

         currentTimeMillis= new AtomicLong();
        velocity= new AtomicReference<>((double) 0);
        translate=new AtomicReference<>();
        isCherryCollected = new AtomicBoolean(false);

        gameOverForThread= new AtomicBoolean(false);

        if (cherry.getPosition() > 0) {
            // cherryPos= random1.nextInt(3,distance-2);
            cherryImage = (ImageView) Components.getComponent(Components.Componenet.ImageView, A.getWidth() + 5 + cherry.getPosition(), 205+incY, "cherry.png", 30, 0);
            root.getChildren().add(cherryImage);
        } else {
            cherryImage = null;
        }
    }

  void handleKeyPressed() {
        isForFlip = new AtomicBoolean(false);
        isDone = new AtomicBoolean(false);

        scene.setOnKeyPressed(event -> {
            if (!isDone.get() && event.getCode() == KeyCode.SPACE) {
                line.setEndY(line.getEndY() - 10);
            } else if (isDone.get()) {
                isForFlip.set(true);
                stickHero.toggleFlip();


            }
        });
    }
     void handleKeyReleased(){
        scene.setOnKeyReleased(event -> {
             int distance=sceneComponent.getDistance();
            if (!isDone.get() && event.getCode() == KeyCode.SPACE) {

                isDone.set(true);
                rotate.setPivotX(line.getStartX());
                rotate.setPivotY(line.getStartY());
                rotate.setAngle(90);

                double move = line.getStartY() - line.getEndY();
                double stickLength=move;
                boolean isGameOver = false;
                double finalPos = line.getStartX() + move;
                if ((finalPos < rectangleB.getX()) || (finalPos > (rectangleB.getX() + rectangleB.getWidth()))) {
                    System.out.println("Game Over");
                    isGameOver = true;
                } else {
                    move = distance + rectangleB.getWidth();
                }
                if (isGameOver) {

                    TranslateTransition translateX = new TranslateTransition(Duration.seconds(1), imageView);

                    translateX.setToX(move + 6);
                    translate.set(translateX);
                    velocity.set((move + 6) / 1000);
                    TranslateTransition translateY = new TranslateTransition(Duration.millis(200), imageView);
                    translateY.setToY(200);
                    translateX.setOnFinished(event2 -> {

                        translateY.play();
                    });
                    translateY.setOnFinished(event2 -> {


                        stage.setScene(gameOver());

                    });
                    translateX.play();

                } else {
                    double coord=imageView.getX();
                    int timeStamp=1500;
                    System.out.println((line.getStartX()+stickLength)+" "+mid.getX());

                    if(line.getStartX()+stickLength>=(mid.getX()-1) && line.getStartX()+stickLength<=(mid.getX()+8)){
                        musicService.playMusic(MusicService.Musics.Bonus);
                        cnt++;
                    }
                    TranslateTransition translateX = new TranslateTransition(Duration.millis(timeStamp), imageView);

                    velocity.set((move + 6) / timeStamp);
                    System.out.println(move+6);
                    translateX.setToX(move + 6);
                    translate.set(translateX);

                    translateX.setOnFinished(event2 -> {
                        try {
                            nextScene();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

                    currentTimeMillis.set(System.currentTimeMillis());
                    translateX.play();

                }


            }
            else if(isForFlip.get()){
                //   Rotate rotate2=new Rotate();
                translate.get().pause();
//
                Scale flipScale = new Scale(1, -1, 0, 200+incY);
                imageView.getTransforms().add(flipScale);
                translate.get().play();
                //imageView.getTransforms().add(rotate1);
                if( stickHero.getIsFlipped()) {
                    //isThread1AlreadySet.set(true);
                    Thread t1 = new Thread(()->{
                        long prev=0;

                        while(cherryImage!=null && !isCherryCollected.get() && !gameOverForThread.get() && stickHero.getIsFlipped() && !Thread.currentThread().isInterrupted() && getDistanceTravelled()<rectangleB.getX()+2) {
                            if (prev != System.currentTimeMillis() - currentTimeMillis.get()) {
                                prev = System.currentTimeMillis() - currentTimeMillis.get();
                                System.out.println(imageView.getX() + " " + velocity.get() + " " + (System.currentTimeMillis() - currentTimeMillis.get())+" "+cherryImage.getX()+" "+ (imageView.getX()+(velocity.get()*(System.currentTimeMillis()-currentTimeMillis.get()))));
                            }
                            try {
                                stickHeroGame.hasCherry();
                            }
                            catch(Exception e){
                                break;
                            }
                            double pos=getDistanceTravelled();
                            System.out.println(pos+" "+cherryImage.getX() );
                            if (stickHeroGame.hasCherry() && pos>=cherryImage.getX() && pos<=cherryImage.getX()+30  ) {
                                stickHeroGame.incCherry();
                                isCherryCollected.set(true);
                                //cherry.setFitHeight(0);
                                //root.getChildren().remove(cherry);
                                System.out.println("Caught");
                                break;

                            }
                        }
                        if(isCherryCollected.get()) {
                            Platform.runLater(() -> root.getChildren().remove(cherryImage));
                        }
                    });
                    t1.start();
                    Thread t2=new Thread(()->{
                        double pos=imageView.getX()+velocity.get()*(System.currentTimeMillis()-currentTimeMillis.get());

                        while(!gameOverForThread.get() && stickHero.getIsFlipped() && !Thread.currentThread().isInterrupted() && (int)(pos)<rectangleB.getX()+2) {
                            pos=imageView.getX()+velocity.get()*(System.currentTimeMillis()-currentTimeMillis.get());

                            if ((pos)>= rectangleB.getX()) {
                                gameOverForThread.set(true);

                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                        if(gameOverForThread.get()){
                            stickHero.toggleFlip();
                            System.out.println(gameOverForThread.get());
                            translate.get().pause();
                            Platform.runLater(()->{stage.setScene(gameOver());});
                        }
                    });
                    t2.start();
                    //isThreadAlreadySet.set(false);




                }
                isForFlip.set(false);
            }
        });
    }
    public final void  playGame(){
        setScene();
        handleKeyPressed();
        handleKeyReleased();
    }
    int getDistanceTravelled(){
        return (int)(imageView.getX()+velocity.get()*(System.currentTimeMillis()-currentTimeMillis.get()));
    }
    abstract void nextScene();
    abstract Scene gameOver() ;
}
