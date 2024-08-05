package com.fullcycle.subscription.domain.account.idp;

public interface IdentityProviderGateway {
    UserId create(User anUser);

    void addUserToGroup(UserId anId, GroupId groupId);

    void removeUserFromGroup(UserId anId, GroupId groupId);
}
