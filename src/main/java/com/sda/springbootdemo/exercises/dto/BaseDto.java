package com.sda.springbootdemo.exercises.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public abstract class BaseDto {

    @Getter
    @Setter
    protected UUID id;
}
