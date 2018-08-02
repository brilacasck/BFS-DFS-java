/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dm_project;

import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author soheil
 */
public class DM_PROJECT extends Application {

    @Override
    public void start(Stage primaryStage) {

        Scanner in = new Scanner(System.in);
        Group root = new Group();
        Graph g = new Graph(in);
        g.get(in);
        g.drawGraph(root);
        System.out.println("\nChoose which algorithm you wanna use:"
                + "\n1.BFS"
                + "\n2.DFS");
        System.out.print("Enter:");
        int k = in.nextInt();
        switch(k){
            case 1:
                g.drawBFS(root);
                break;
            case 2:
                g.drawDFS(root);
                break;
            default:
                System.out.println("Wrong");
                System.exit(0);
                break;
        }
        System.out.println(g.toString());
        
        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
