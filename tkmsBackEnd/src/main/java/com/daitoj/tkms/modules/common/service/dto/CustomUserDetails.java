package com.daitoj.tkms.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** ログイン結果 */
@Schema(name = "CustomUserDetails", description = "ログイン結果")
public class CustomUserDetails implements UserDetails {
  /** アカウント区分 */
  @Schema(description = "アカウント区分")
  @Setter
  @Getter
  private String accountKubun;

  /** ユーザーID */
  private String loginId;

  /** パスワード */
  private String password;

  /** 機能ID */
  @Schema(description = "機能ID")
  @Setter
  @Getter
  private String pgId;

  /** 権限を設定 */
  @Setter private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

  /** コンストラクタ */
  public CustomUserDetails() {}

  /**
   * コンストラクタ
   *
   * @param loginId ユーザーID
   * @param password パスワード
   */
  public CustomUserDetails(String loginId, String password) {
    this.loginId = loginId;
    this.password = password;
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return loginId;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
