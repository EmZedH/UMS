package Logic.Payment;

import Logic.Interfaces.PaymentInterface;
import UI.Utility.DisplayUtility;

public class DebitCard implements PaymentInterface{
    boolean status = false;
    @Override
    public void pay() {
        DisplayUtility.singleDialogDisplay("Processing Payment through Debit Card");
        this.status = true;
    }

    @Override
    public boolean paymentStatus() {
        DisplayUtility.singleDialogDisplay("Debit Card payment success!!!");
        return this.status;
    }
    
}
