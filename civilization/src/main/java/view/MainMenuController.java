package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.graphicModel.ProfilePhoto;
import model.graphicModel.UserProfile;

import java.util.Objects;

public class MainMenuController extends Controller {
    private static MainMenuController controller;
    public static MainMenuController getInstance() {
        if (controller == null)
            controller = new MainMenuController();
        return controller;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenuGraphics.setStage(stage);
        BorderPane parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenu.fxml")));
        notRegisteredGame(parent);
        registeredScoreBord(parent);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void scoreTable(Stage stage) throws Exception {
        //BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/scoreTable.fxml")));
        stage = new Stage();
        TableView<UserProfile> table = new TableView<>();


        TableColumn<UserProfile, ProfilePhoto> photo = new TableColumn<>("photo");
        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));

        TableColumn<UserProfile, String> userNameCol = new TableColumn<>("username");
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserProfile, Integer> score = new TableColumn<>("score");
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObservableList<UserProfile> list = FXCollections.observableArrayList(UserProfile.allUserProfiles);
        table.setItems(list);



        table.getColumns().addAll(photo, userNameCol, score);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        userNameCol.setSortType(TableColumn.SortType.DESCENDING);

        loggedInUserBackground(userNameCol, score);

        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(table);

        stage.setTitle("TableView");

        Scene scene = new Scene(root, 660, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void loggedInUserBackground(TableColumn<UserProfile, String> userNameCol, TableColumn<UserProfile, Integer> score) {

        userNameCol.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER);

                        //We get here all the info of the Person of this row
                        UserProfile auxPerson = getTableView().getItems().get(getIndex());

                        if (auxPerson.getUsername().equals(View.getIsLoggedIn().getUsername())) {
                            setTextFill(Color.RED);
                            setStyle("-fx-background-color: #baffba;");
                        } else  {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });



        score.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {

                        setText(String.valueOf(item));
                        setAlignment(Pos.CENTER);

                        //We get here all the info of the Person of this row
                        UserProfile auxPerson = getTableView().getItems().get(getIndex());

                        if (auxPerson.getUsername().equals(View.getIsLoggedIn().getUsername())) {
                            setTextFill(Color.RED);
                            setStyle("-fx-background-color: #baffba;");
                        } else  {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });
    }


    public void registeredScoreBord(BorderPane pane) {
        if (View.getIsLoggedIn() != null) {
            Button button = new Button("Score Table");
            Button logout = new Button("logout");
            Button newGame = new Button("Game Menu");
            newGame.setMinWidth(500);
            newGame.getStyleClass().add("button");
            logout.setMinWidth(500);
            logout.getStyleClass().add("button");
            button.setMinWidth(500);
            button.getStyleClass().add("button");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        scoreTable(ProfileMenuGraphics.getStage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            VBox vBox = (VBox) pane.getChildren().get(0);
            vBox.getChildren().add(vBox.getChildren().size() - 2, button);
            vBox.getChildren().add(vBox.getChildren().size() - 2, logout);
            vBox.getChildren().add(0, newGame);
            logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        View.setIsLoggedIn(null);
                        goToRegister(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            newGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        newGame(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public void notRegisteredGame(BorderPane parent) {
        if (View.getIsLoggedIn() == null) {
            Button button = new Button("new Game without login");
            button.setMinWidth(500);
            button.getStyleClass().add("button");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        newGameWithoutLogin(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            VBox vBox = (VBox) parent.getChildren().get(0);
            vBox.getChildren().add(0, button);
        }
    }

    public void newGame(MouseEvent event) throws Exception {
        if (View.getIsLoggedIn() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Login First!");
            alert.show();
        } else {
            GameMenu.getInstance().start(ProfileMenuGraphics.getStage());
        }
    }

    public void newGameWithoutLogin(MouseEvent event) throws Exception {
        GameMenu.getInstance().start(ProfileMenuGraphics.getStage());
    }

    public void goToRegister(MouseEvent event) throws Exception {
        View.setIsLoggedIn(null);
        LoginMenuController.getInstance().register(ProfileMenuGraphics.getStage());
    }


}
