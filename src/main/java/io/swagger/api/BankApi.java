package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.PostAmntById;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Api(value = "bank", description = "the bank API")
public interface BankApi {

    @ApiOperation(value = "Check account balance", nickname = "getBalance", notes = "", response = BigDecimal.class, tags={ "bank", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = BigDecimal.class),
            @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value = "/balance/{accountId}",
            method = RequestMethod.GET)
    ResponseEntity<BigDecimal> getBalance(@Min(1L)@ApiParam(value = "ID of the account balance that needs to be fetched",required=true) @PathVariable("accountId") Long accountId);

    @ApiOperation(value = "Add funds to account balance", nickname = "depositByAcccountId", notes = "", tags={ "bank", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    @RequestMapping(value = "/deposit",
            method = RequestMethod.POST)
    ResponseEntity<BigDecimal> depositByAcccountId(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PostAmntById postAmtById);

    @ApiOperation(value = "Withdraw funds", nickname = "withdrawByAccountId", notes = "", tags={ "bank", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    @RequestMapping(value = "/withdraw",
            method = RequestMethod.POST)
    ResponseEntity<BigDecimal> withdrawByAccountId(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PostAmntById postAmntById);

}
