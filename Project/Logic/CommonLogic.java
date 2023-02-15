package Logic;
import UI.CommonDisplay;
import UI.CommonUI;

public class CommonLogic {
    static int choice;
    public static void startup(){
        choice = CommonUI.startupPageInput();
        if(choice == 1){
            userSelect();
        }
        else if(choice == 2){
            CommonUI.thankYou();
            System.exit(0);
        }
    }
    

    public static void userSelect(){
        choice = CommonUI.loginUserSelectInput();
        switch(choice){
            case 1:
            System.out.println("Yet To Implement");
            break;
            case 2:
            System.out.println("Yet To Implement");
            break;
            case 3:
            CollegeAdminLogic.startup();
            break;
            case 4:
            SuperAdminLogic.startup();
            break;
            case 5:
            CommonUI.thankYou();
            System.exit(0);
        }
        }

        public static void sqlError(int user,int uID) {
                int inp = CommonUI.sqlErrorPageInput();
                switch (inp) {
                    case 1:
                    switch (user) {
                        case 1:
                        break;
                        case 2:
                        break;
                        case 3:
                        CollegeAdminLogic.startup(uID);
                        break;
                        case 4:
                        SuperAdminLogic.startup(uID);
                        break;
                    }
                    break;
                    case 2:
                    userSelect();
                    break;
                    default:
                    CommonDisplay.properPage();
                    sqlError(user,uID);
                }
        }

        public static void wrongCredentials(int user, int uID){
                int inp = CommonUI.wrongCredentialsInput();
                switch (inp) {
                    case 1:
                    switch (user) {
                        case 1:
                        break;
                        case 2:
                        break;
                        case 3:
                        CollegeAdminLogic.startup(uID);
                        break;
                        case 4:
                        SuperAdminLogic.startup(uID);
                        break;
                    }
                    break;
                    case 2:
                    CommonLogic.userSelect();
                    break;
                    default:
                    CommonDisplay.properPage();
                    wrongCredentials(user,uID);
                }
        }

}
