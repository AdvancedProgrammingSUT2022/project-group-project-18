package view;

import controller.RegisterMenuController;
import enums.Message;
import enums.Regexes;

import java.util.regex.Matcher;

public class RegesterMenuView extends View{
    RegisterMenuController controller = new RegisterMenuController();

    @Override
    public void run() {
        String input = getInput();
        Matcher matcher;
        if((matcher = Regexes.getCommand(input, Regexes.MENUENTER)) != null)
            System.out.println(controller.enterMenu(matcher).toString());

        else if( Regexes.getCommand(input, Regexes.EXITMENU) != null)
            controller.exitMenu();
        else if(Regexes.getCommand(input, Regexes.SHOW_CURRENT_MENU) != null)
            System.out.println(controller.showCurrentMenu().toString());
        else if((matcher = Regexes.getCommand(input, Regexes.CREAT_NEW_USER)) != null)
            System.out.println(controller.creatUser(matcher));
        else if((matcher = Regexes.getCommand(input, Regexes.USER_LOGIN)) != null) {
            Message message = controller.loginUser(matcher);
            System.out.println(message);
            if(message.equals(Message.LOGIN_USER)) controller.goToMainMenu();
        } else
            System.out.println(Message.INVALID);

        this.run();



    }

}
