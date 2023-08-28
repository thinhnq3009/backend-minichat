package com.eco.beminichat.controllers.rest;

import com.eco.beminichat.response.ListAccountResponse;
import com.eco.beminichat.response.base.ResponseObject;
import com.eco.beminichat.response.base.ResponseObjects;
import com.eco.beminichat.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/account")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;


//    @GetMapping("/find-friends")
//    public ResponseEntity<ResponseObject<ListAccountResponse>> getAccounts(
//            @RequestParam(value = "query", required = false) String query,
//            @RequestParam(value = "page", defaultValue = "0") Integer page,
//            @RequestParam(value = "size", defaultValue = "10") Integer limit
//    ) {
//
//        Pageable pageable = Pageable.ofSize(limit).withPage(page);
//
//        ListAccountResponse response = accountService.getAccountByQuery(query, pageable);
//
//        return ResponseObjects.getResponseEntity(response, "Get accounts successfully");
//    }
}
