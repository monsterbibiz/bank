package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Account;
import io.swagger.model.PostAmntById;
import io.swagger.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class BankController implements BankApi {
    private static final Logger log = LoggerFactory.getLogger(BankController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final AccountRepository accountRepository;

    @Autowired
    public BankController(ObjectMapper objectMapper, HttpServletRequest request, AccountRepository accountRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<BigDecimal> getBalance(@Min(1L) @ApiParam(value = "ID of the account balance that needs to be fetched", required = true) @PathVariable("accountId") Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (!accountOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account account = accountOptional.get();

        return new ResponseEntity<>(new BigDecimal(account.getBalance()), HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> depositByAcccountId(@ApiParam(value = "", required = true) @Valid @RequestBody PostAmntById postAmntById) {
        Optional<Account> accountOptional = accountRepository.findById(postAmntById.getAccountId());

       if(!accountOptional.isPresent()) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       Account account = accountOptional.get();
       float balance = account.getBalance();
       balance += postAmntById.getAmount().floatValue();
       account.setBalance(balance);
       accountRepository.save(account);

       return new ResponseEntity<>(new BigDecimal(account.getBalance()), HttpStatus.OK);
    }

    public ResponseEntity<BigDecimal> withdrawByAccountId(@ApiParam(value = "", required = true) @Valid @RequestBody PostAmntById postAmntById) {
        Optional<Account> accountOptional = accountRepository.findById(postAmntById.getAccountId());

        if (!accountOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account account = accountOptional.get();
        float balance = account.getBalance();

        if (balance - postAmntById.getAmount().floatValue() < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        balance -= postAmntById.getAmount().floatValue();
        account.setBalance(balance);
        accountRepository.save(account);

        return new ResponseEntity<>(new BigDecimal(account.getBalance()), HttpStatus.OK);
    }
}
