package com.example.booktree.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserPasswordRequestDto {

    @NotEmpty
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함한 8~20자리."
    )

    private String changePassword;

    @NotEmpty
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함한 8~20자리."
    )

    private String beforePassword;

}
