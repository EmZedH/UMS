package Logic;


import java.sql.SQLException;

import Model.FactoryDAO;

public class Main{

    public static void main(String[] args){
        
        ModuleExecutor moduleExecutor = new ModuleExecutor();
        
        try {
            moduleExecutor.executeModule(new WelcomePage(moduleExecutor, new FactoryDAO()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}