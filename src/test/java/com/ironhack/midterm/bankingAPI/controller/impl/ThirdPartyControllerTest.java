package com.ironhack.midterm.bankingAPI.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dao.other.Address;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dao.roles.Admin;
import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyTransactionDTO;
import com.ironhack.midterm.bankingAPI.enums.Status;
import com.ironhack.midterm.bankingAPI.repository.accounts.AccountRepository;
import com.ironhack.midterm.bankingAPI.repository.accounts.SavingAccountRepository;
import com.ironhack.midterm.bankingAPI.repository.other.AddressRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.AccountHolderRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.RoleRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.ThirdPartyRepository;
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
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ThirdPartyControllerTest {
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
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Admin testAdmin;
    private AccountHolder accountHolder1;
    private AccountHolder accountHolder2;
    private Address address1;
    private SavingAccount saving1;
    private SavingAccount saving2;
    private ThirdParty thirdPartyTestUser;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private void clearDB(){
        thirdPartyRepository.deleteAll();
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
        thirdPartyTestUser = thirdPartyRepository.save(new ThirdParty(
                "ThirdPartyTestUser",
                "pw123456",
                "Third Party Test User",
                "hash1234456"
        ));
    }
    @AfterEach
    void tearDown(){
        clearDB();
    }

    @Test
    void createThirdParty_positive_3rdPartyCreated() throws Exception {
        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO("3rd_party_test","12345","Test 3rd party","12345");
        MvcResult result = mockMvc
                .perform(post("/api/v1/admin/create_third_party").with(user(new CustomUserDetails(testAdmin)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(thirdPartyDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        ThirdParty resultObject = objectMapper.readValue(resultString,ThirdParty.class);
        Assertions.assertEquals(resultObject.getName(),"Test 3rd party");
    }
    @Test
    void sendFunds_positive_fundsAddedToAccount() throws Exception {
        SavingAccount receiver = savingAccountRepository.findAll().get(0);
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = new ThirdPartyTransactionDTO(
                receiver.getId(),
                new BigDecimal("100"),
                receiver.getSecretKey());
        MvcResult result = mockMvc
                .perform(post("/api/v1/third_party/send")
                        .header("hashedKey", thirdPartyTestUser.getHashKey())
                        .with(user(new CustomUserDetails(thirdPartyTestUser)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(thirdPartyTransactionDTO)))
                .andExpect(status().isAccepted())
                .andReturn();
        BigDecimal exceptedBalance = receiver.getBalance().add(thirdPartyTransactionDTO.getAmount());
        Account accountAfterUpdate = accountRepository.findById(receiver.getId()).get();
        Assertions.assertEquals(0,exceptedBalance.compareTo(accountAfterUpdate.getBalance()));
    }
    @Test
    void receiveFunds_positive_fundsSubtractedFromTheAccount() throws Exception {
        SavingAccount receiver = savingAccountRepository.findAll().get(0);
        ThirdPartyTransactionDTO thirdPartyTransactionDTO = new ThirdPartyTransactionDTO(
                receiver.getId(),
                new BigDecimal("100"),
                receiver.getSecretKey());
        MvcResult result = mockMvc
                .perform(post("/api/v1/third_party/receive")
                        .header("hashedKey", thirdPartyTestUser.getHashKey())
                        .with(user(new CustomUserDetails(thirdPartyTestUser)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(thirdPartyTransactionDTO)))
                .andExpect(status().isAccepted())
                .andReturn();
        BigDecimal exceptedBalance = receiver.getBalance().subtract(thirdPartyTransactionDTO.getAmount());
        Account accountAfterUpdate = accountRepository.findById(receiver.getId()).get();
        Assertions.assertEquals(0,exceptedBalance.compareTo(accountAfterUpdate.getBalance()));
    }
}
