package com.akichou.domain;

import com.akichou.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

/**
 * Represents a logged-in user with additional details for authentication and authorization.
 *
 * <p>
 * This class implements the {@link UserDetails} interface from Spring Security to provide user
 * details needed for authentication and authorization purposes. It wraps around a {@link User}
 * object and includes additional fields such as permissions.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/07/20 Sat.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    private List<String> permissions ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {    // Get userName in database
        return user.getUserName();
    }

    @Override
    public String getPassword() {    // Get password in database
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {    // Is login status NOT expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {     // Is account NOT locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {    // Is login credentials NOT expired
        return true;
    }

    @Override
    public boolean isEnabled() {    // Is user account enabled
        return true;
    }
}