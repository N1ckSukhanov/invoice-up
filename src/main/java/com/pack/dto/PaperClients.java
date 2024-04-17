package com.pack.dto;

import com.pack.entity.Contact;
import com.pack.entity.Contract;
import lombok.Data;

@Data
public class PaperClients {
    private final Contract contract = new Contract();
    private final Contact contact1 = new Contact();
    private final Contact contact2 = new Contact();
}
