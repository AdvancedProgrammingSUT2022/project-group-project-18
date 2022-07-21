package controller;

import enums.BuildingEnum;
import enums.UnitEnum;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Building;
import model.City;
import model.Tile;
import model.unit.Settler;
import model.unit.Unit;
import view.View;

import java.awt.*;
import java.util.Objects;

public class BackController extends Application {
    @FXML
    private ScrollPane scrollPane;
    int endOfX = 1540;
    int endOfY = 730;
    int firstOfX = 180;
    int firstOfY = 10;
    int oUp = 1;
    int oDown = 1;
    int oRight = 1;
    int oLeft = 1;
    int numOfUp = 0;
    int numOfDown = 0;
    @FXML
    private Button found;
    private AnchorPane pane;
    int size = 0;
    int numOfRight = 0;
    int counter = 0;
    boolean flag = false;
    boolean moveFlag = false;


    @Override
    public void start(Stage stage) throws Exception {
        this.pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/GameBackground.fxml")));
        this.scrollPane = createScrollPane(pane);
        Scene scene = new Scene(this.scrollPane);


        foor();

        foundCity(pane);
        moving(scene, pane, stage);

        scene.setOnMouseClicked(event -> {
            System.out.println("--------");
            Tile ti;
            if ((ti = Tile.getTileFromCoordinate(event.getX(), event.getY())) != null) {
                System.out.println(ti.getX() + " " + ti.getY());
            }
            /*System.out.println(event.getX());
            System.out.println(event.getY());*/
            System.out.println();
        });
        scrollPane.requestFocus();
        /*pane.requestFocus();
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode().getName()) {
                    case "Left":
                        moveLeft();
                        break;
                    case "Right":
                        moveRight();
                        break;
                    case "Up":
                        moveUp();
                        break;
                    case "Down":
                        moveDown();
                        break;
                }
            }
        });
*/


        for (Tile tile : Tile.getTiles()) {
                Popup popup = popup(tile.getX(), tile.getY());
                tile.setOnMouseEntered(event-> {
                    popup.show(stage);
                });
                tile.setOnMouseExited(event -> {
                    popup.hide();
                });
        }

        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setPrefSize(1500, 780);
        scroll.setContent(layout);
        return scroll;
    }

    @FXML
    private void moveDown() {
        numOfDown++;
        for (Tile tile : Tile.getTiles()) {
            tile.setLayoutY(tile.getLayoutY() - 10);
        }
        if (numOfDown % 5 == 0 && numOfDown < 20)
            newRowOfTiles(1);
    }

    private void moveUp() {
        numOfUp++;
        for (Tile tile : Tile.getTiles()) {
            tile.setLayoutY(tile.getLayoutY() + 10);

        }
        if (numOfUp % 5 == 0 && numOfUp < 20)
            newRowOfTiles(0);
    }

    private void moveRight() {
        numOfRight++;
        for (Tile tile : Tile.getTiles()) {
            tile.setLayoutX(tile.getLayoutX() - 10);
        }
        if (numOfRight % 5 == 0 && numOfRight < 20)
            newColumn(0);
    }

    private void moveLeft() {
        for (Tile tile : Tile.getTiles()) {
            tile.setLayoutX(tile.getLayoutX() + 10);
        }
    }

        public void moving (Scene scene, AnchorPane pane, Stage stage){

            Button move = new Button("move");
            move.setLayoutX(1);
            move.setLayoutY(469);
            move.setPrefHeight(63);
            move.setPrefWidth(77);
            for (Unit unit : View.getInCity().getUnits()) {
                unit.setOnMouseClicked(event -> {
                    if (!flag) {
                        move.setOnMouseClicked(event1 -> {
                            if (!moveFlag) {
                                scene.setOnMouseClicked(event2 -> {
                                    Tile tile = Tile.getTileFromCoordinate(event2.getX(), event2.getY());
                                    int distance = unit.distance(tile.getX(), tile.getY());
                                    if (distance <= unit.getMovement()) {
                                        unit.move(tile.getX() - 25, tile.getY() - 50);
                                        unit.setUnitTile(tile);
                                        unit.minesMovement(distance);
                                    } else {
                                        System.out.println("you can't go there!");
                                    }


                                    moveFlag = true;
                                });
                            } else {
                                moveFlag = false;
                            }
                        });
                        unit.setEffect(new Glow());
                        flag = true;
                    } else {
                        unit.setEffect(null);
                        flag = false;
                    }
                });
            }
            pane.getChildren().add(move);

        }

        public void foundCity (AnchorPane pane){
            Settler settler = (Settler) UnitEnum.getUnits(UnitEnum.SETTLER);
            Tile tile = Tile.getTileFromCoordinate(500, 500);
            settler.setX(tile.getX() - 40);
            settler.setY(tile.getY() - 40);
            System.out.println(tile.getTileType());
            Button button = new Button("found  city");
            button.setLayoutX(1);
            button.setLayoutY(406);
            button.setPrefHeight(63);
            button.setPrefWidth(77);

            button.setOnMouseClicked(event -> {
                double x = settler.getX();
                double y = settler.getY();
                Tile capital = Tile.getTileFromCoordinate(x, y);
                x = capital.getX();
                y = capital.getY();
                City city = new City(x, y);
                city.setX(x);
                city.setY(y);

                city.addTileToCity(capital);
                capital = Tile.getTileFromCoordinate(x - 80, y - 135);
                System.out.println(capital.getX() + " " + capital.getY());
                city.addTileToCity(capital);
                capital = Tile.getTileFromCoordinate(x + 80, y - 135);
                System.out.println(capital.getX() + " " + capital.getY());
                city.addTileToCity(capital);
                capital = Tile.getTileFromCoordinate(x - 160, y);
                System.out.println(capital.getX() + " " + capital.getY());
                city.addTileToCity(capital);
                capital = Tile.getTileFromCoordinate(x + 160, y);
                System.out.println(capital.getX() + " " + capital.getY());
                city.addTileToCity(capital);
                capital = Tile.getTileFromCoordinate(x - 80, y + 135);
                System.out.println(capital.getX() + " " + capital.getY());
                city.addTileToCity(capital);
                capital = Tile.getTileFromCoordinate(x + 80, y + 135);
                System.out.println(capital.getX() + " " + capital.getY());
                city.addTileToCity(capital);
                pane.getChildren().remove(settler);
                size = pane.getChildren().size() - 1;
                Building building = BuildingEnum.makeBuilding(BuildingEnum.PALACE);
                building.getIcon().setFitHeight(120);
                building.getIcon().setFitWidth(120);
                building.getIcon().setX(x - 55);
                building.getIcon().setY(y - 60);
                pane.getChildren().add(size, building.getIcon());
                pane.getChildren().remove(button);

            });
            size = pane.getChildren().size() - 1;
            pane.getChildren().add(size, button);
            pane.getChildren().add(size + 1, settler);
        }

        public void foor () {

            String[] name = {"dasht", "grass", "grass", "grass", "grass", "grass", "grass", "grass", "kavir", "kooh", "ocean", "sand", "sand"
                    , "sand", "grass", "snow", "tappe", "tappe", "tondra"};
            int rand = 0;
            for (int i = -10; i < 15; i++) {
                for (int j = -10; j < 9; j++) {
                    rand = (int) Math.floor(Math.random() * 100);
                    String back = name[rand % 19];
                    if (j % 2 == 0) {
                        int x = 100 + (i * 160);
                        int y = 100 + (j * 135);
                        Tile tile = new Tile(x, y, back);
                        //if (x + 80 < 1500 && x - 80 > 0 && y - 45 > 0) {
                            tile.setCoordinates(x, y);
                            tile.setImage(back);
                            if (y > 600 || x > 1200 || y < 100 || x < 200) {
                                settingEffect(tile);
                            }

                            pane.getChildren().add(tile);
                        //}
                    } else {
                        int x = 180 + (i * 160);
                        int y = 100 + (j * 135);
                        Tile tile = new Tile(x, y, back);
                        //if (x + 80 < 1500 && x - 80 > 0 && y + 45 < 780 && y - 45 > 0) {
                            tile.setCoordinates(x, y);
                            tile.setImage(back);
                            if (y > 600 || x > 1200 || y < 200 || x < 200) {
                                settingEffect(tile);
                            }
                            pane.getChildren().add(tile);
                        //}

                    }
                }
            }
        }

        public void settingEffect (Tile tile){
            GaussianBlur gaussianBlur = new GaussianBlur();
            gaussianBlur.setRadius(20);
            tile.setEffect(gaussianBlur);
        }

        public void popUp () {

        }

        public void newRowOfTiles ( int side){ // side =0 -> up     side =1 -> down
            String[] name = {"dasht", "grass", "grass", "grass", "grass", "grass", "grass", "grass", "kavir", "kooh", "ocean", "sand", "sand"
                    , "sand", "grass", "snow", "tappe", "tappe", "tondra"};
            if (side == 0) {
                if (oUp % 2 == 0) {
                    for (int i = 0; i < 9; i++) {
                        int rand = (int) Math.floor(Math.random() * 100);
                        String back = name[rand % 19];
                        Tile tile = new Tile(100 + i * 160, firstOfY, back);
                        tile.setCoordinates(100 + i * 160, firstOfY);
                        tile.setImage(back);
                        pane.getChildren().add(tile);
                    }
                }
                if (oUp % 2 != 0) {
                    for (int i = 0; i < 8; i++) {
                        int rand = (int) Math.floor(Math.random() * 100);
                        String back = name[rand % 19];
                        Tile tile = new Tile(180 + i * 160, firstOfY, back);
                        tile.setCoordinates(180 + i * 160, firstOfY);
                        tile.setImage(back);
                        pane.getChildren().add(tile);
                    }
                }
                oUp++;
            } else {
                if (oDown % 2 == 0) {
                    for (int i = 0; i < 9; i++) {
                        int rand = (int) Math.floor(Math.random() * 100);
                        String back = name[rand % 19];
                        Tile tile = new Tile(100 + i * 160, endOfY, back);
                        tile.setCoordinates(100 + i * 160, endOfY);
                        tile.setImage(back);
                        settingEffect(tile);
                        pane.getChildren().add(tile);
                    }
                }
                if (oDown % 2 != 0) {
                    for (int i = 0; i < 8; i++) {
                        int rand = (int) Math.floor(Math.random() * 100);
                        String back = name[rand % 19];
                        Tile tile = new Tile(180 + i * 160, endOfY, back);
                        tile.setCoordinates(180 + i * 160, endOfY);
                        tile.setImage(back);
                        settingEffect(tile);
                        pane.getChildren().add(tile);
                    }
                }
                oDown++;
            }
            firstOfY -= 90;
            endOfY += 90;
        }

        public void newColumn ( int side){ //side=0 -> Right      side=0 -> Left
            String[] name = {"dasht", "grass", "grass", "grass", "grass", "grass", "grass", "grass", "kavir", "kooh", "ocean", "sand", "sand"
                    , "sand", "grass", "snow", "tappe", "tappe", "tondra"};
            if (side == 0) {
                for (int i = 0; i < 9; i++) {
                    int rand = (int) Math.floor(Math.random() * 100);
                    String back = name[rand % 19];
                    Tile tile = new Tile(endOfX, firstOfY + 150 * i, back);
                    tile.setCoordinates(endOfX, firstOfY + 150 * i);
                    tile.setImage(back);
                    pane.getChildren().add(tile);
                }
                oUp++;
            } else {
                if (oDown % 2 == 0) {
                    for (int i = 0; i < 9; i++) {
                        int rand = (int) Math.floor(Math.random() * 100);
                        String back = name[rand % 19];
                        Tile tile = new Tile(100 + i * 160, endOfY, back);
                        tile.setCoordinates(100 + i * 160, endOfY);
                        tile.setImage(back);
                        pane.getChildren().add(tile);
                    }
                }
                if (oDown % 2 != 0) {
                    for (int i = 0; i < 8; i++) {
                        int rand = (int) Math.floor(Math.random() * 100);
                        String back = name[rand % 19];
                        Tile tile = new Tile(180 + i * 160, endOfY, back);
                        tile.setCoordinates(180 + i * 160, endOfY);
                        tile.setImage(back);
                        pane.getChildren().add(tile);
                    }
                }
                oDown++;
            }
            firstOfX -= 160;
            endOfX += 160;
        }

        public Popup popup ( double x, double y){
            Tile tile = Tile.getTileFromCoordinate(x, y);
            Popup popup = new Popup();
            String gold = String.valueOf(tile.goldOutput);
            javafx.scene.image.Image image = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/images/icon.png").toExternalForm()));
            ImageView imageView = new ImageView(image);
            Label label = new Label("gold: " + gold +
                    "\nproduction: " + tile.prodution +
                    "\nfood: " + tile.foodOutput +
                    "\ncombat modifiers: " + tile.cm +
                    "\nmovement cost: " + tile.cost, imageView);
            popup.getContent().add(label);
            label.setMinWidth(150);
            label.setMinHeight(100);
            label.setStyle(" -fx-text-fill: #851111;");
            label.setStyle(" -fx-background-color: rgba(255,255,255,0.6);");
            label.setFont(Font.font(""));
            popup.setOpacity(1);
            popup.setX(1200);
            popup.setY(400);
            popup.setAutoHide(true);
            return popup;
        }
    }
