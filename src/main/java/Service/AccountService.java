package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    
    //adds new user
    //NEED LOGIC TO VERIFY NO DUPLICATE ACCOUNT ID
    public Account addAccount(Account account){
        return account;
    }

    /*
    public Account getAccount(int account_id){
        account = AccountDAO.getAccount(account_id);
        return account;
    }
    */

}
