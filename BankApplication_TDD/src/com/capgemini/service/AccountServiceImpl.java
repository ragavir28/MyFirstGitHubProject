package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialAmountException
	{
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
			}
	/*@Override
	public boolean depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException {
		if(accountRepository.searchAccount(accountNumber) != null)
		{
			if(amount > 0){			
				return true;
			}
		}
		else{
			throw new InvalidAccountNumberException();
		}
		return false;
	}*/
	
	
/*	@Override
	public boolean withdrawAmount(int accountNumber, int amount)
			throws InsufficientBalanceException, InvalidAccountNumberException {
		Account account = new Account();
		account.setAmount(500);
		if(accountRepository.searchAccount(accountNumber) != null)
		{
			if(account.getAmount() > amount){
				return true;
				
			}
			else{
				throw new InsufficientBalanceException();
			}
		}
		else if(accountRepository.searchAccount(accountNumber) == null){
			throw new InvalidAccountNumberException();
		}
		return false;
		*/
	//}
	
	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InsufficientBalanceException, InvalidAccountNumberException {
		Account account = new Account();
		account.setAmount(500);
		int amt = account.getAmount();
		if(accountRepository.searchAccount(accountNumber) != null)
		{
			if(amt > amount){
				amt = amt - amount;
				return amt;
				
			}
			else{
				throw new InsufficientBalanceException();
			}
		}
		else if(accountRepository.searchAccount(accountNumber) == null){
			throw new InvalidAccountNumberException();
		}
		return amt;
		
	}
	@Override
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException {
		Account account = new Account();
		account.setAmount(500);
		int amt = account.getAmount();
		if(accountRepository.searchAccount(accountNumber) != null)
		{
			if(amount > 0){	
				amt = amt +amount;
				return amt;
			}
		}
		else{
			throw new InvalidAccountNumberException();
		}
		return amt;
	}
}
