package com.vva.auth.basic.app;

import com.google.common.collect.ImmutableList;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {

    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<String> getSecurityList() {
        return ImmutableList.of(
                "hasAuthority('ROLE_ADMIN')",
                "MyService.getSecurityList()",
                "one",
                "two",
                "three"
        );
    }
}
