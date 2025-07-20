package com.rafiyad.bank.features.account.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    @RequestMapping("/api/v1/hello")
    public String hello() {
        return "Hello World";
    }
    @RequestMapping("/api/v1/mono")
    public Mono<String> account() {
        return Mono.just("Hello World in mono");
    }

    @RequestMapping("/api/v1/res")
    public ResponseEntity<Mono<String>>  account2() {
        return ResponseEntity.ok(Mono.just("Hello World in mono Request Entity"));
    }
}
