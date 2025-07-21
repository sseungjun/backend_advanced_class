package com.core.backend_advanced.api.post.entity;

import lombok.Getter;

@Getter
public enum PostStatus {
    VALID,
    WAITING,
    COMPLETED,
    CANCELLED
}
