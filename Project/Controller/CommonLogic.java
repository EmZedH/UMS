package Controller;
import View.CommonDisplay;
import View.CommonUI;

public class CommonLogic {
    static int inputChoice;
    public static void startup(){
        inputChoice = CommonUI.startupPageInput();

        if(inputChoice == 1){
            userSelect();
        }
        else if(inputChoice == 2){
            CommonUI.thankYou();
            System.exit(0);
        }
    }
    

    public static void userSelect(){
        inputChoice = CommonUI.loginUserSelectInput();
        switch(inputChoice){

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

        public static void sqlError(int userType,int userID) {
                int input = CommonUI.sqlErrorPageInput();
                switch (input) {
                    case 1:
                    switch (userType) {

                        //STUDENT LOGIN
                        case 1:
                            break;

                        //PROFSSOR LOGIN
                        case 2:
                            break;

                        //COLLEGE ADMIN LOGIN
                        case 3:
                            CollegeAdminLogic.startup(userID);
                            break;

                        //SUPER ADMIN LOGIC
                        case 4:
                            SuperAdminLogic.startup(userID);
                            break;
                    }
                    break;
                    case 2:
                        userSelect();
                        break;
                    default:
                        CommonDisplay.properPage();
                        sqlError(userType,userID);
                }
        }

        public static void wrongCredentials(int userType, int userID){
                int inp = CommonUI.wrongCredentialsInput();
                switch (inp) {
                    case 1:
                        switch (userType) {

                            case 1:
                                break;

                            case 2:
                                break;
                                
                            case 3:
                                CollegeAdminLogic.startup(userID);
                                break;

                            case 4:
                                SuperAdminLogic.startup(userID);
                                break;
                        }
                        break;

                    case 2:
                        CommonLogic.userSelect();
                        break;

                    default:
                        CommonDisplay.properPage();
                        wrongCredentials(userType,userID);
                }
        }

}
