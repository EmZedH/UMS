package Logic;

import java.sql.SQLException;

import Model.FactoryDAO;

public class Main{

    public static void main(String[] args){
        ModuleExecutor module = new ModuleExecutor();

        try {
            module.executeModule(new WelcomePage(module, new FactoryDAO()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}