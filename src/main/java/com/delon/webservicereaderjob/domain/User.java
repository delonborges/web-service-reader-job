package com.delon.webservicereaderjob.domain;

public record User(Integer id,
                   String name,
                   String email,
                   String gender,
                   String status) {}
