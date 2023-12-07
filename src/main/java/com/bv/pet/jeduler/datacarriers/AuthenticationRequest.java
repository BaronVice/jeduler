package com.bv.pet.jeduler.datacarriers;

public record AuthenticationRequest (
    String username,
    String password
){
}
