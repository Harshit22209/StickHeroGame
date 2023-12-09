package com.game.stick_hero;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//Used Factory design Pattern
public class Components {
    static enum Componenet{
        Button,Label,Text,ImageView,Rectangle,Line
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
                Image image  = new Image(Components.class.getResourceAsStream(input));
                ImageView imageView=new ImageView(image);
                imageView.setFitHeight(fsize1);
                imageView.setPreserveRatio(true);
                imageView.setX(x);

                imageView.setY(y);
                return imageView;
            case Rectangle:
                Rectangle rec=new Rectangle();
                rec.setX(x);
                rec.setY(y);
                rec.setHeight(fsize1);
                rec.setWidth(fsize2);
                return rec;
            case Line:
                Line line=new Line();
                line.setStartX(x);
                line.setEndX(fsize1);
                line.setStartY(y);
                line.setEndY(fsize2);
                return line;
            default:
                return null;
        }
        //return null;
    }
}
