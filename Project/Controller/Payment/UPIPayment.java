package Controller.Payment;

import View.Utility.DisplayUtility;

public class UPIPayment implements Payable{
    boolean status = true;

    @Override
    public void pay() {
        DisplayUtility.singleDialogDisplay("Processing Payment through UPI");
        this.status = true;
    }

    @Override
    public boolean paymentStatus() {
        DisplayUtility.singleDialogDisplay("UPI payment success!!!");
        return this.status;
    }
}
