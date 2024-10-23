package com.example.spring;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.LocalDate;
import java.time.ZonedDateTime;
@Builder
@With
public record Todolist( String id,
                       String description,
                        Status status) {
}
