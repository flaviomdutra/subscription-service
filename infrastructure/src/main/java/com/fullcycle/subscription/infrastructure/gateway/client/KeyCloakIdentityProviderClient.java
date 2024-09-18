package com.fullcycle.subscription.infrastructure.gateway.client;

import com.fullcycle.subscription.domain.account.idp.GroupId;
import com.fullcycle.subscription.domain.account.idp.IdentityProviderGateway;
import com.fullcycle.subscription.domain.account.idp.User;
import com.fullcycle.subscription.domain.account.idp.UserId;
import com.fullcycle.subscription.domain.utils.IdUtils;
import org.springframework.stereotype.Component;

@Component
public class KeyCloakIdentityProviderClient implements IdentityProviderGateway {
    @Override
    public UserId create(User anUser) {
        return new UserId(IdUtils.uniqueId());
    }

    @Override
    public void addUserToGroup(UserId anId, GroupId groupId) {

    }

    @Override
    public void removeUserFromGroup(UserId anId, GroupId groupId) {

    }
}
