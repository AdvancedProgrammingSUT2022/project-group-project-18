package view;

import javafx.application.Application;
import model.BaseCivilization;
import model.City;
import model.graphicModel.User;

import java.util.Locale;
import java.util.Scanner;

public abstract class View extends Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static String inMenu = "Login Menu";
    private static User isLoggedIn = null;
    private static City inCity = null;
    private static BaseCivilization civilization = null;

    public static void setIsLoggedIn(User isLoggedIn) {View.isLoggedIn = isLoggedIn;}
    public static User getIsLoggedIn() {return isLoggedIn;}
    public void removeIsLoggedIn() { View.isLoggedIn = null;}
    public static Scanner getScanner() {
        return View.scanner;
    }
    protected static String getInput() {
        return View.getScanner().nextLine().trim().toLowerCase(Locale.ROOT);
    }
    public static void setInMenu(String inMenu) {
        View.inMenu = inMenu;
    }
    public static String getInMenu() {
        return inMenu;
    }


    public static void setCivilization(BaseCivilization civilization) {View.civilization = civilization;}
    public static BaseCivilization getCivilization() {return civilization;}
    public static void setInCity(City inCity) {View.inCity = inCity;}
    public static City getInCity() {return inCity; }
}
