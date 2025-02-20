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
    //NEED LOGIC TO VERIFY NO DUPLICATE ACCOUNT ID
    public Account addAccount(Account account){
        return accountDao.insertAccount(account);
    }

    /*
    public Account getAccount(int account_id){
        account = AccountDAO.getAccount(account_id);
        return account;
    }
    */

}
