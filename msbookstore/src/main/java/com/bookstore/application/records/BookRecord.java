package com.bookstore.application.records;

import java.util.Set;
import java.util.UUID;

public record BookRecord(
        String title,
        UUID publisherId,
        Set<UUID> AuthorsIds,
        String reviewComment) {
}
