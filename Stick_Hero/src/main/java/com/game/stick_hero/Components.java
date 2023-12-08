package com.game.stick_hero;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Components {
    static enum Componenet{
        Button,Label,Text,ImageView,
    }

    public static Object getComponent(Componenet c,double x,double y,String input,double fsize1,double fsize2){
        switch (c){
            case Text:
                Text text=new Text();
                text.setX(x);
                text.setY(y);
                text.setText(input);
                text.setFont(Font.font("Verdona",fsize1));
                return text;
            case Button:
                Button btn=new Button();
                btn.setPrefSize(fsize1,fsize2);
                btn.setLayoutX(x);
                btn.setLayoutY(y);
                btn.setText(input);
                return btn;
            case ImageView:
                Image image  = new Image(Components.class.getResourceAsStream("stickHeroMan.png"));



        }
    }
}
