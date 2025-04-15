package com.example.booktree.user.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserPostRequestDto {

    @NotNull
    private Long roleId;

    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자를 포함한 8~20자리."
    )
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-1234-5678이어야 합니다.")
    private String phoneNumber;

    private String username;

    private String ssoProvider;

    private String socialId;


}
