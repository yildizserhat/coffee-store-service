package com.yildiz.serhat.coffeestoreservice.controller.model.request;

import javax.validation.constraints.NotNull;


public record LoginRequestDTO(@NotNull String username,
                              @NotNull String password) {

}
