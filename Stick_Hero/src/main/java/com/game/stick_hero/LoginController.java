package com.game.stick_hero;

import com.game.stick_hero.StickHeroComponents.StickHero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private Label welcomeText;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorOutput;



    @FXML
    void goToLogin(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    void checkAndLogin(ActionEvent e) throws IOException {
        StickHero stickHero=null;
        ;
        try {

            stickHero=DataBaseService.getMainUser(id.getText(), name.getText(), password.getText());

            if(stickHero==null){
                stickHero=new StickHero(name.getText(),id.getText(),0,new ArrayList<>());
                //stickHeroGame=new StickHeroGame(name.getText(),id.getText());
                DataBaseService.addUser(name.getText(),id.getText(),password.getText());
            }

        } catch (UserNotFoundException ex) {
           // throw new RuntimeException(ex);
            errorOutput.setText(ex.getMessage());
            return;
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        Parent root = loader.load();
        //userName.setText(name.getText());
        GameController controllerB = loader.getController();

        // Create an object to pass to ControllerB

        controllerB.receiveData(stickHero);

        // Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Parent root= FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
//
//       Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//       scene=new Scene(root);
//       stage.setScene(scene);
//       //stage.setUserData(new StickHeroGame(name.getText(),id.getText()));
//       stage.show();


    }
}