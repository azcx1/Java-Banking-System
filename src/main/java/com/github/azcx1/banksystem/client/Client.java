package com.github.azcx1.banksystem.client;

import java.util.UUID;

public interface Client {
    UUID getId();
    ContactDetails getContactDetails();
}
