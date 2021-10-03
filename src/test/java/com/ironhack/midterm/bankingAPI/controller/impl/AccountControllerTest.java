package com.ironhack.midterm.bankingAPI.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dao.other.Address;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dao.roles.Admin;
import com.ironhack.midterm.bankingAPI.dto.BalanceDTO;
import com.ironhack.midterm.bankingAPI.enums.Status;
import com.ironhack.midterm.bankingAPI.repository.accounts.AccountRepository;
import com.ironhack.midterm.bankingAPI.repository.accounts.SavingAccountRepository;
import com.ironhack.midterm.bankingAPI.repository.other.AddressRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.AccountHolderRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.RoleRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.security.CustomUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerTest {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository rolesRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    SavingAccountRepository savingAccountRepository;
    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Admin testAdmin;
    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private Address address1;
    private SavingAccount saving1;
    private SavingAccount saving2;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private void clearDB(){
        accountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        addressRepository.deleteAll();
        rolesRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        testAdmin =userRepository.save(new Admin("TestAdmin","TestAdmin",passwordEncoder.encode("1234")));
        address1 = addressRepository.save(new Address("Test City","12345","Test street","A1/2","Poland"));
        accountHolder1 = accountHolderRepository.save(new AccountHolder("Test1",passwordEncoder.encode("1234"),"Test Account1",new Date(111111L),address1));
        accountHolder2 = accountHolderRepository.save(new AccountHolder("Test2",passwordEncoder.encode("1234"),"Test Account2",new Date(111111L),address1));
        //Money balance, AccountHolder primaryOwner, BigDecimal interestRate, BigDecimal minimumBalance, String secretKey, Date creationDate, Status status
        saving1 = savingAccountRepository.save(new SavingAccount(
                new BigDecimal("2000"),
                accountHolder1,
                new BigDecimal("0.2"),
                new BigDecimal("200"),
                "secKeyTest",
                new Date(1234567L),
                Status.ACTIVE
                ));
        saving2 = savingAccountRepository.save(new SavingAccount(
                new BigDecimal("20000"),
                accountHolder2,
                new BigDecimal("0.2"),
                new BigDecimal("101"),
                "secKeyTest2",
                new Date(1234567L),
                Status.ACTIVE
        ));
    }
    @AfterEach
    void tearDown(){
        clearDB();
    }

    @Test
    void findAllAccounts_positive_foundAccountsWithGoodCredentials() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/api/v1/admin/account").with(user(new CustomUserDetails(testAdmin))))
                .andExpect(status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        List<Account> resultList = Arrays.asList(objectMapper.readValue(resultString,SavingAccount[].class));
        Assertions.assertEquals(2,resultList.size());
    }
    @Test
    void findAllAccounts_negative_forbiddenRequest() throws Exception {
            int status =mockMvc
                    .perform(get("/api/v1/admin/account").with(user(new CustomUserDetails(accountHolder1))))
                    .andExpect(status().isForbidden())
                    .andReturn().getResponse().getStatus();
        Assertions.assertEquals(403,status);
    }

    @Test
    void findAccountById_positive_accountFound() throws Exception {
        Long refAccountId = accountRepository.findAll().get(0).getId();
        MvcResult result = mockMvc
                .perform(get("/api/v1/admin/account/"+refAccountId).with(user(new CustomUserDetails(testAdmin))))
                .andExpect(status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        Account resultObject = objectMapper.readValue(resultString,SavingAccount.class);
        Assertions.assertEquals(refAccountId,resultObject.getId());
    }
    @Test
    void findAccountById_negative_forbiddenRequest() throws Exception {
            Long refAccountId = accountRepository.findAll().get(0).getId();
            int status =mockMvc
                    .perform(get("/api/v1/admin/account/"+refAccountId).with(user(new CustomUserDetails(accountHolder1))))
                    .andExpect(status().isForbidden())
                    .andReturn().getResponse().getStatus();
            Assertions.assertEquals(403,status);
    }

    @Test
    void findAllUserAccounts_positive_correctListOfAccounts() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/api/v1/my_accounts").with(user(new CustomUserDetails(accountHolder1))))
                .andExpect(status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        List<Account> resultList = Arrays.asList(objectMapper.readValue(resultString,SavingAccount[].class));
        Assertions.assertEquals(1,resultList.size());
    }
    @Test
    void findAllUserAccounts_negative_forbiddenRequest() throws Exception {
        //Admins don't have access to my_accounts, since in this context they do not own any.
        int status = mockMvc
                    .perform(get("/api/v1/my_accounts").with(user(new CustomUserDetails(testAdmin))))
                    .andExpect(status().isForbidden())
                    .andReturn()
                .getResponse()
                .getStatus();
        Assertions.assertEquals(403,status);
    }

    @Test
    void updateBalanceById() throws Exception {
        BalanceDTO balanceDTO = new BalanceDTO(new BigDecimal("2345676.54"));
        Long refAccountId = accountRepository.findAll().get(0).getId();
        //unchecked optional class .get() because we got this object one line ago
        BigDecimal oldBalanceValue = accountRepository.findById(refAccountId).get().getBalance();
        MvcResult result = mockMvc
                .perform(put("/api/v1/admin/balance/"+refAccountId).with(user(new CustomUserDetails(testAdmin)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(balanceDTO.getBalance())))
                .andExpect(status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        Account resultObject = objectMapper.readValue(resultString,SavingAccount.class);
        Assertions.assertNotEquals(oldBalanceValue,balanceDTO.getBalance());
        Assertions.assertEquals(balanceDTO.getBalance(),resultObject.getBalance());
    }
    @Test
    void activateAccount_positive_statusUpdated() throws Exception {
        Account sampleAccount = new SavingAccount(
                new BigDecimal("2000"),
                accountHolder2,
                new BigDecimal("0.2"),
                new BigDecimal("200"),
                "secKeyTest",
                new Date(1234567L),
                Status.FROZEN
        );
        SavingAccount sampleSavingAccount = (SavingAccount)accountRepository.save(sampleAccount);
        Long sampleAccountId = sampleSavingAccount.getId();
        mockMvc
                .perform(patch("/api/v1/admin/activate/" + sampleAccountId).with(user(new CustomUserDetails(testAdmin))))
                .andExpect(status().isNoContent())
                .andReturn();
        //update sample account
        sampleSavingAccount = (SavingAccount)accountRepository.findById(sampleAccountId).get();
        Assertions.assertEquals(Status.ACTIVE,sampleSavingAccount.getStatus());

    }
}