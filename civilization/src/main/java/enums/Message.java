package enums;

public enum Message {
        LOGINERROR("please login first"),
        INVALID("invalid command"),
        IVALIDENTERMENU("menu navigation is not possible"),
        MAINMENU("Main Menu"),
        LOGINMENU("Login Menu"),
        INVALID_MENU_NAME("menu name is invalid"),
        USERCREAT("user create successfully!"),
        USER_EXIST_USERNAME_ONE("user with username "),
        USER_EXIST_USERNAME_TWO(" already exists"),
        USER_EXIST_NICKNAME_ONE("user with nickname "),
        LOGIN_USER("user logged in successfully!"),
        NOT_MATCH("Username and password didn't match!"),
        USER_NOT_EXIST("one of players username dose not exists!"),
        PLAY_GAME("finaly welcome to the game!"),
        USER_LOGOUT("user logged out successfully!"),
        CHANGE_NICKNAME("nickname changed successfully!"),
        CHANGE_PASSWORD("password changed successfully!"),
        PASS_INVALID("current password is invalid"),
        SAME_PASS("please enter a new password");


    private String regex;

    Message (String regex) {
        this.regex = regex;
    }

    @Override
    public String toString() {
        return this.regex;
    }

}