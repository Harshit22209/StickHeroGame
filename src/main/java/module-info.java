module com.game.stick_hero {
    requires javafx.controls;
    requires javafx.fxml;

            
        requires org.controlsfx.controls;


            requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires javafx.media;

    opens com.game.stick_hero to javafx.fxml;
    exports com.game.stick_hero;
}