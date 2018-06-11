package com.sda.springbootdemo.exercises.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ClientDetails implements org.springframework.security.oauth2.provider.ClientDetails {

    private String clientId;
    private String clientSecret;
    private Set<String> scope = Collections.emptySet();
    private Set<String> resourceIds = Collections.emptySet();
    private Set<String> authorizedGrantTypes = Collections.emptySet();
    private Set<String> registeredRedirectUris;
    private Set<String> autoApproveScopes;
    private List<GrantedAuthority> authorities = Collections.emptyList();
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

    /** Initializes ClientDetails with properties from given client.
     *
     * @param client instance of custom Client
     */
    public ClientDetails(Client client) {
        this.clientId = client.getClientId();

        this.resourceIds = toSet(client.getResourceIds());
        this.scope = toSet(client.getScope());
        this.authorizedGrantTypes = toSet(client.getAuthorizedGrantTypes());
        this.authorities = toAuthorities(client.getAuthorities());
        this.registeredRedirectUris = toSet(client.getRegisteredRedirectUris());

        this.clientSecret = client.getClientSecret();
        this.accessTokenValiditySeconds = client.getAccessTokenValiditySeconds();
        this.refreshTokenValiditySeconds = client.getRefreshTokenValiditySeconds();

        String json = client.getAdditionalInformation();
        if (null != json) {
            try {
                this.additionalInformation = new ObjectMapper().readValue(json, Map.class);
            } catch (Exception ex) {
                // TODO: add logger
            }
        }

        this.autoApproveScopes = toSet(client.getAutoApproveScopes());

        if (null == authorizedGrantTypes || authorizedGrantTypes.isEmpty()) {
            this.authorizedGrantTypes = new HashSet<>(Arrays.asList("authorization_code", "refresh_token"));
        }
    }

    private Set<String> toSet(String arg) {
        if (StringUtils.hasText(arg)) {
            Set<String> resources = StringUtils.commaDelimitedListToSet(arg);

            if (!resources.isEmpty()) {
                return resources;
            }
        }

        return null;
    }

    private List<GrantedAuthority> toAuthorities(String arg) {
        return StringUtils.hasText(arg)
                ? AuthorityUtils.commaSeparatedStringToAuthorityList(arg)
                : null;
    }

    @Override
    public boolean isSecretRequired() {
        return clientSecret != null && !clientSecret.isEmpty();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUris;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }
}
