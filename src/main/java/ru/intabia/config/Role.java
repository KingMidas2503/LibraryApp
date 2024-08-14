package ru.intabia.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    LIBRARIAN("Librarian"),
    READER("Reader"),;

    final String value;

}
