package com.core.backend_advanced.api.members.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address {

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "basic_address")
    private String basicAddress;

    @Column(name = "detail_address")
    private String detailAddress;
}
