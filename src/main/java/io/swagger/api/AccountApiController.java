package io.swagger.api;

import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-19T00:17:48.039Z")

@Controller
public class AccountApiController implements AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request, AccountRepository accountRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Void> createAccount(@ApiParam(value = "Created user object" ,required=true )  @Valid @RequestBody Account body) {
        accountRepository.save(body);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAccountById(@ApiParam(value = "",required=true) @PathVariable("accountId") Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accountRepository.deleteById(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Account> getAccountById(@ApiParam(value = "",required=true) @PathVariable("accountId") Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return new ResponseEntity<Account>(account.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Account> updateAccountById(@ApiParam(value = "",required=true) @PathVariable("accountId") Long accountId, @ApiParam(value = "Updated account object" ,required=true )  @Valid @RequestBody Account updatedAccount) {
        //Set the updated object's Id to the passed in Id for the equality check
        updatedAccount.id(accountId);
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (!accountOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account theAccount = accountOptional.get();
        if (updatedAccount.equals(theAccount)) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        theAccount.username(updatedAccount.getUsername())
                .firstName(updatedAccount.getFirstName())
                .lastName(updatedAccount.getLastName())
                .email(updatedAccount.getEmail())
                .phone(updatedAccount.getPhone())
                .balance(updatedAccount.getBalance());

        accountRepository.save(theAccount);
        return new ResponseEntity<Account>(theAccount, HttpStatus.OK);
    }

}
