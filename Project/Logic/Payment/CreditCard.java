package Logic.Payment;

import Logic.Interfaces.PaymentInterface;
import UI.Utility.DisplayUtility;

public class CreditCard implements PaymentInterface{
    boolean status = false;
    @Override
    public void pay() {
        DisplayUtility.singleDialogDisplay("Processing Payment through Credit Card");
        this.status = true;
    }

    @Override
    public boolean paymentStatus() {
        DisplayUtility.singleDialogDisplay("Credit Card payment success!!!");
        return this.status;
    }
    
}
