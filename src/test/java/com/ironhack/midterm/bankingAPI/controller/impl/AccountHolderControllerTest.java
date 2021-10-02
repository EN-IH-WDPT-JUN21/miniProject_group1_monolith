package com.ironhack.midterm.bankingAPI.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dao.other.Address;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dao.roles.Admin;
import com.ironhack.midterm.bankingAPI.dto.AccountHolderDTO;
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
import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {
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
    void createAccountHolder() throws Exception {
            Address mailingAddress = new Address("Poznan","62030","Testowa","11","Poland");
            AccountHolderDTO newAccountHolder = new AccountHolderDTO(
                "TestAccount1",
                    "12345",
                    "Adam Test New",
                    new Date(1123434535L),
                    "Poznan",
                    "11-222",
                    "Poland",
                    "Testowa",
                    "10",
                    "Paris",
                    "PAS122",
                    "France",
                    "TestStreet",
                    "10A"
            );
            MvcResult result = mockMvc
                    .perform(post("/api/v1/admin/create_account_holder").with(user(new CustomUserDetails(testAdmin)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newAccountHolder)))
                    .andExpect(status().isCreated())
                    .andReturn();
            String resultString = result.getResponse().getContentAsString();
            AccountHolder resultObject = objectMapper.readValue(resultString,AccountHolder.class);
            Assertions.assertEquals(resultObject.getName(),"Adam Test New");
        }
}