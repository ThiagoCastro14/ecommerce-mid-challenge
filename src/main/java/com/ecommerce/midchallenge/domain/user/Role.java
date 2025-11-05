package com.ecommerce.midchallenge.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_roles",
       indexes = {
           @Index(name = "idx_role_name", columnList = "name")
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)") 
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
