package com.example.tasklist2.web.dto.user;


import com.example.tasklist2.web.dto.validation.OnCreate;
import com.example.tasklist2.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
@Schema(description = "User DTO")
public class UserDto {
    @Schema(name = "User id", example = "1")
    @NotNull(message = "id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "username", example = "John Doe")
    @NotNull(message = "name must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Name length must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String name;


    @Schema(description = "user email", example = "johndoc@gmail.com")
    @NotNull(message = "Username must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Username length must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String username;


    @Schema(description = "User crypted password", example = "$2a$10$ENLl3Fg6PEFIeKAaXvlAU.1Ynp03sH.asVGsPylAz.RkBWxcKlT7O")
    @NotNull(message = "password must be not null", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Schema(description = "user password confirmation", example = "$2a$10$ENLl3Fg6PEFIeKAaXvlAU.1Ynp03sH.asVGsPylAz.RkBWxcKlT7O")
    @NotNull(message = "password confirmation must be not null", groups = {OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;
}
