/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dm_project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author soheil
 */
public class Graph {

    private int size; //number of vertices
    private int graph[][]; //the data of the graph with 0,1 or the weight
    private Circle node[]; //showing the vertices with circles and saving them in an array
    private ArrayList<Line> edge; //showing and saving the edges between vertices in an arraylist
    private ArrayList<Integer> bfs; //saving the vertices ordering by bfs algorithm
    private ArrayList<Integer> dfs; //saving the vertices ordering by dfs algorithm

    public Graph(Scanner in) {
        System.out.print("Enter the size: ");
        size = in.nextInt();
        graph = new int[size][size];
        node = new Circle[size];
        edge = new ArrayList<Line>();
        bfs = new ArrayList<>(size);
        dfs = new ArrayList<>(size);
    }

    public Graph(int size) {
        this.size = size;
        graph = new int[size][size];
        node = new Circle[size];
        edge = new ArrayList<Line>();
        bfs = new ArrayList<>(size);
        dfs = new ArrayList<>(size);
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @param i
     * @param j
     * @return the value of the selected array
     */
    public int getGraph(int i, int j) {
        return graph[i][j];
    }

    /**
     *
     * @param i
     * @param j
     * @param value
     */
    public void setGraph(int i, int j, int value) {
        graph[i][j] = value;
    }

    public void get(Scanner in) {
        System.out.println("Enter the array contents regularly in the serial:");
        System.out.println("ATTENTION: the array should be homolographic");
        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    graph[i][j] = in.nextInt();
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("array index out of bound Exception accured");
        }
        System.out.print("Enter the start point bigger than zero: ");
        int start = in.nextInt();
        bfs(start - 1);
        dfs(start - 1);
    }

    public void print() {
        System.out.println("size = " + size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private void bfs(int sourceNode) {
        System.out.print("BFS: ");
        Queue<Integer> ProcessQ = new LinkedList<>();
        ArrayList<Boolean> visited = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            visited.add(false);
        }
        ProcessQ.add(sourceNode);
        visited.set(sourceNode, true);
        while (!ProcessQ.isEmpty()) {
            int currentNode = ProcessQ.element();
            ProcessQ.remove();
            bfs.add(currentNode + 1);
            for (int i = 0; i < size; i++) {
                if (graph[currentNode][i] != 0 && !visited.get(i)) {
                    ProcessQ.add(i);
                    visited.set(i, true);
                }
            }
        }
    }

    private void dfs(int sourceNode) {
        System.out.print("\nDFS: ");
        boolean flag = false;
        Stack<Integer> ProcessS = new Stack<>();
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            visited.add(false);
        }
        ProcessS.add(sourceNode);
        visited.set(sourceNode, true);
        dfs.add(sourceNode + 1);
        while (!ProcessS.isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (graph[ProcessS.peek()][i] != 0 && !visited.get(i)) {
                    ProcessS.add(i);
                    dfs.add(i + 1);
                    visited.set(i, true);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ProcessS.pop();

            }
            flag = false;
        }
    }

    public void drawGraph(Group root) {

        for (int i = 0; i < size; i++) {
            node[i] = new Circle(17, Color.LIGHTCORAL);
            node[i].setEffect(new DropShadow(1, 3, 3, Color.GRAY));

            node[i].setCenterX((Math.random()) * 1000 + 200);
            node[i].setCenterY((Math.random()) * 500 + 200);

            root.getChildren().add(node[i]);
        }

        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (graph[i][j] > 0) {
                    edge.add(new Line(node[i].getCenterX(), node[i].getCenterY(), node[j].getCenterX(), node[j].getCenterY()));
                }
                Text t = new Text(node[i].getCenterX() - 4, node[i].getCenterY() + 4, "" + (i + 1));
                t.setFill(Color.AQUA);
                t.setStroke(Color.WHITE);
                root.getChildren().add(t);
            }
        }
        root.getChildren().addAll(edge);
        for (int i = 0; i < edge.size(); i++) {
            edge.get(i).toBack();
        }
    }

    public void drawBFS(Group root) {
        Button b = new Button("START"); //creat a button
        Circle c = new Circle(17, null); //creat the circle will be moved on the vertices
        c.setVisible(false); //پنهان کردن دایره ی توخالی در ابتدای کار
        c.setStrokeWidth(3);
        c.setStroke(Color.BLACK);
        Text result = new Text(100, 30, "RESULT: ");
        result.setStrokeWidth(10);
        result.setFill(Color.BLACK);

        root.getChildren().add(b);
        root.getChildren().add(c);
        root.getChildren().add(result);

        b.setOnAction(new EventHandler<ActionEvent>() {
            int count = 0;//شمارنده 

            //شروع کار با کلیک کردن روی دکمه ی تعریف شده
            @Override
            public void handle(ActionEvent event) {
                c.setVisible(true);
                //تغییر نام دکمه
                b.setText("NEXT");
                // در این قسمت به دلیل تمام شدن پردازش باید کاری کرد که برنامه بسته شود
                if (count == size) {
                    c.setVisible(false);
                    System.out.println();
                    System.exit(0);
                } else {
                    //جابه جایی دایره ی توخالی بر روی دیگر راس ها
                    c.setCenterX(node[bfs.get(count) - 1].getCenterX());
                    c.setCenterY(node[bfs.get(count) - 1].getCenterY());

                    //تغیرر رنگ دایره های دیده شده
                    node[bfs.get(count) - 1].setEffect(new Glow(.1));
                    node[bfs.get(count) - 1].setFill(Color.YELLOWGREEN);

                    //اضافه شدن شماره ی اعداد دیده شده به صفحه ی گرافیکی
                    root.getChildren().add(new Text(160 + 15 * count, 30, "" + bfs.get(count)));
                    count++;

                    //تغییر نام دکمه
                    if (count == size) {
                        b.setText("END");
                    }
                }
            }
        });
    }

    public void drawDFS(Group root) {
        Button b = new Button("START"); //creat a button
        Circle c = new Circle(17, null); //creat the circle will be moved on the vertices
        c.setVisible(false); //پنهان کردن دایره ی توخالی در ابتدای کار
        c.setStrokeWidth(3);
        c.setStroke(Color.BLACK);
        Text result = new Text(100, 30, "RESULT: ");
        result.setStrokeWidth(10);
        result.setFill(Color.BLACK);

        root.getChildren().add(b);
        root.getChildren().add(c);
        root.getChildren().add(result);

        b.setOnAction(new EventHandler<ActionEvent>() {
            int count = 0;//شمارنده 

            //شروع کار با کلیک کردن روی دکمه ی تعریف شده
            @Override
            public void handle(ActionEvent event) {
                c.setVisible(true);
                //تغییر نام دکمه
                b.setText("NEXT");
                // در این قسمت به دلیل تمام شدن پردازش باید کاری کرد که برنامه بسته شود
                if (count == size) {
                    c.setVisible(false);
                    System.out.println();
                    System.exit(0);
                } else {
                    //جابه جایی دایره ی توخالی بر روی دیگر راس ها
                    c.setCenterX(node[dfs.get(count) - 1].getCenterX());
                    c.setCenterY(node[dfs.get(count) - 1].getCenterY());

                    //تغیرر رنگ دایره های دیده شده
                    node[dfs.get(count) - 1].setEffect(new Glow(.1));
                    node[dfs.get(count) - 1].setFill(Color.YELLOWGREEN);

                    //اضافه شدن شماره ی اعداد دیده شده به صفحه ی گرافیکی
                    root.getChildren().add(new Text(160 + 15 * count, 30, "" + dfs.get(count)));
                    count++;

                    //تغییر نام دکمه
                    if (count == size) {
                        b.setText("END");
                    }
                }
            }
        });
    }
    
    @Override
    public String toString(){
        return "BFS: "+bfs.toString()+"\nDFS: "+dfs.toString();
    }
}
