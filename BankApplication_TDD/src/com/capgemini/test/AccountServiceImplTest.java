package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
public class AccountServiceImplTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService=new AccountServiceImpl(accountRepository);
		
	}
	
	/*
	 * test cases for create account
	 * 1. when the amount is less than 500 system should generate exception
	 * 2. when the valid(101,5000) info is passed account should be created successfully
	 */
	
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101,400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{    
		
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(102, 5000));
	}
	
	/*
	 * test cases for withdrawing money
	 * 1. When the account number is invalid  system should throw InvalidAccountNumberException
	 * 2. When there amount specified is greater than the balance system should throw InsufficientBalanceException
	 * 2. when the valid(101,5000) info is passed amount should be withdrawn successfully	
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenThereIsNoSufficientBalanceForWithdrawal() throws InsufficientBalanceException, InvalidAccountNumberException{
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(500);
		when(accountRepository.save(account)).thenReturn(true);
		when(accountRepository.searchAccount(102)).thenReturn(account);
		accountService.withdrawAmount(102,2000);
		
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenInvalidAccountNumberIsPassedForWithdrawal() throws InsufficientBalanceException, InvalidAccountNumberException{
		accountService.withdrawAmount(120,50);
	}
	
	@Test 
	public void whenValidInfoIsPassedForWithdrawal() throws InsufficientBalanceException, InvalidAccountNumberException{
		Account account = new Account();
		when(accountRepository.searchAccount(102)).thenReturn(account);
		assertEquals(475,accountService.withdrawAmount(102,25));
	}
	/*
	 * test cases for depositing money
	 * 1. When the account number is invalid  system should throw InvalidAccountNumberException
	 * 2. When valid info is passed amount should be deposited succesfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountNumberIsInvalidWhileDepositingAmount() throws InvalidAccountNumberException
	{
		accountService.depositAmount(-101,400);
	}
	@Test
	public void whenValidInfoIsPassedForDepositingMoney() throws InvalidAccountNumberException
	{
		Account account = new Account();
		account.setAccountNumber(102);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		when(accountRepository.searchAccount(102)).thenReturn(account);
		assertEquals(600,accountService.depositAmount(102,100));
	}
	
	
	
}
