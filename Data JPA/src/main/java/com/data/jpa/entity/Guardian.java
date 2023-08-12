package com.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Guardian {

    @Column(name = "guardian_name")
    private String name;

    @Column(name = "guardian_email")
    private String email;

    @Column(name = "guardian_mobile")
    private String mobile;

}
