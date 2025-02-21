package Service;

import Model.Account;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import DAO.AccountDAO;

public class AccountService {
    
    //creates object
    AccountDAO accountDao;
    MessageDAO messageDao;

    //instiantiates object
    public AccountService(){
        this.accountDao = new AccountDAO();
        this.messageDao = new MessageDAO();
    }


    //adds new user
    public Account addAccount(Account account){
        if (!account.getUsername().isBlank() && account.getPassword().length() > 3)
            return accountDao.insertAccount(account);
        else {
            return null;
        }
    }

    /*
    public Account getAccount(int account_id){
        account = AccountDAO.getAccount(account_id);
        return account;
    }
    */

}
