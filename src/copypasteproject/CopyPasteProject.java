/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copypasteproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author IT-0
 */
public class CopyPasteProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("Image/logo.png"));
//        stage.initStyle(StageStyle.TRANSPARENT);
//
//        scene.setFill(Color.TRANSPARENT);
//        scene.getStylesheets().add("CSS/newCascadeStyleSheet.css");       
        stage.setScene(scene);
        stage.setResizable(false);
        
        stage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
