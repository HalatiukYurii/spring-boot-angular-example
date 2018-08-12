package com.sda.springbootdemo.exercises.controller;

import com.sda.springbootdemo.exercises.exception.NotFoundException;
import com.sda.springbootdemo.exercises.exception.UnauthorizedException;
import com.sda.springbootdemo.exercises.model.User;
import com.sda.springbootdemo.exercises.repository.UserRepository;
import com.sda.springbootdemo.exercises.security.AuthorizationHelper;
import com.sda.springbootdemo.exercises.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthorizationHelper authorizationHelper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        if (!authorizationHelper.getCurrentUser().isAdmin()) {
            throw new UnauthorizedException("You don't have permission to get other users info!");
        }
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable("id") UUID userId) {
        if (!hasPermissionToUser(userId)) {
            throw new UnauthorizedException("You don't have permission to get this user info!");
        }
        return userService.get(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid User user, BindingResult bindingResult) {
        return userService.save(user, bindingResult);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody @Valid User user,
                       @PathVariable("id") UUID userId,
                       BindingResult bindingResult) {
        if (!hasPermissionToUser(userId)) {
            throw new UnauthorizedException("You don't have permission to edit this user!");
        }

        user.setId(userId);
        return userService.update(user, userId, bindingResult);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") UUID userId) {
        if (!hasPermissionToUser(userId)) {
            throw new UnauthorizedException("You don't have permission to delete this user!");
        }

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id: %s not found", userId));
        }

        userRepository.deleteById(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout(OAuth2Authentication auth) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        String token = details.getTokenValue();
        tokenStore.removeAccessToken(new DefaultOAuth2AccessToken(token));
        return "User logged out successfully";
    }

    private boolean hasPermissionToUser(UUID userId) {
        User currentUser = authorizationHelper.getCurrentUser();
        return currentUser.isAdmin() || userId.equals(currentUser.getId());
    }
}
