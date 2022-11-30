package org.acme.module;

public record Users(
        String user_id ,
        String username ,
        String desc,
        String phone,
        String email,
        String status
){}
