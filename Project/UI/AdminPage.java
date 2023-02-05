package UI;

import Database.Admin;

public class AdminPage {
    public static void display(Admin admin){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("              Admin Page");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("         1. User");
        System.out.println("         2. Course");
        System.out.println("         3. Department");
        System.out.println("         4. Record");
        System.out.println("         5. Registered Students");
        System.out.println("         6. Section");
        System.out.println("         7. Tests");
        System.out.println("         8. Transactions");
        System.out.println("         9. Log Out");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("Name: "+admin.getName() + "                  ID: "+admin.getAdminID());
        System.out.println("---------------------------------------");
        System.out.println();
    }
}
