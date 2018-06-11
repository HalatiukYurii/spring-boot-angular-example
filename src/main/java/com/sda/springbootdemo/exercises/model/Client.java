package com.sda.springbootdemo.exercises.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_details")
public class Client {

    @Id
    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "scope")
    private String scope;

    @Column(name = "resource_ids")
    private String resourceIds;

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    @Column(name = "redirect_uri")
    private String registeredRedirectUris;

    @Column(name = "auto_approve")
    private String autoApproveScopes;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "access_token_validity")
    private Integer accessTokenValiditySeconds;

    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

    public Client(String clientId, String clientSecret, String authorities, String authorizedGrantTypes, String resourceIds, String scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorities = authorities;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.resourceIds = resourceIds;
        this.scope = scope;
    }
}