package com.akichou.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Select Conditions Dto
 *
 * <p>
 * Data Transfer Object for filtering user information.
 * </p>
 *
 * <p>
 * This DTO encapsulates the conditions used to filter users, including the username,
 * phone number, and status. It is designed to be used in scenarios where user data
 * needs to be queried or filtered based on these attributes.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/19 Fri.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "UserSelectConditionsDto", description = "Conditions to filter user dto")
public class UserSelectConditionsDto {

    @Schema(name = "userName", description = "user username")
    private String userName ;

    @Schema(name = "phoneNumber", description = "user phone number")
    private String phoneNumber ;

    @Schema(name = "status", description = "user status ( 0 -> usable, 1 -> forbidden )")
    private String status ;
}
