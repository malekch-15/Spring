package com.example.spring.ChatGpt;

import java.util.List;

public record chatgptrequest(String model, List<chatgpMessage> messages,
                             chatgptformat response_format) {
}
