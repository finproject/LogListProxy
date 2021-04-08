package com.example.proxyarraylist.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
@AllArgsConstructor
public class Pair {
    private Field key;
    private String value;
}
