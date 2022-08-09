package org.volcamp.api.utils.token.roles;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.volcamp.api.exception.ForbiddenException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.json.JsonArray;
import javax.json.JsonString;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Priority(10)
@Interceptor
@Roles
public class RolesInterceptor {
    private final JsonWebToken jwt;

    @Inject
    public RolesInterceptor(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Roles rolesEnum = context.getMethod().getAnnotation(Roles.class);

        if (rolesEnum == null || rolesEnum.value() == null) {
            return context.proceed();
        }

        List<String> roles = Arrays.stream(rolesEnum.value())
                .map(RolesEnum::toString)
                .map(String::toLowerCase)
                .toList();

        JsonArray tokenRoles = jwt.getClaim("permissions");

        if (tokenRoles == null || tokenRoles.isEmpty()) {
            throw new ForbiddenException();
        }

        HashSet<String> tokenRolesAsString = new HashSet<>(tokenRoles.getValuesAs(JsonString.class).stream().map(JsonString::getString).toList());

        if (!tokenRolesAsString.containsAll(roles)) {
            throw new ForbiddenException();
        }

        return context.proceed();
    }
}
