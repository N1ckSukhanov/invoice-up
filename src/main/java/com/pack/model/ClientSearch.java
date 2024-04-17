package com.pack.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ClientSearch {
    private String clientShortName;
    private String clientStatus;
    private String clientPrSanctions;
}
