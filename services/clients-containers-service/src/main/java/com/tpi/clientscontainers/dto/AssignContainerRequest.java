package com.tpi.clientscontainers.dto;

import jakarta.validation.constraints.NotNull;

public record AssignContainerRequest(@NotNull Long containerId, @NotNull Long clientId) {
}
