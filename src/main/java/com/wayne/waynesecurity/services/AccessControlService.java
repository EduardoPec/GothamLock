package com.wayne.waynesecurity.services;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.wayne.waynesecurity.model.enums.AccessArea;
import com.wayne.waynesecurity.model.enums.Role;

@Component
public class AccessControlService {
	
	private static final Map<Role, Set<AccessArea>> MAPA = new EnumMap<>(Role.class);
    static {
        MAPA.put(Role.ADMIN_SEGURANCA, EnumSet.allOf(AccessArea.class));
        MAPA.put(Role.GERENTE, EnumSet.of(AccessArea.ARMAZEM, AccessArea.GARAGEM, AccessArea.LABORATORIO));
        MAPA.put(Role.FUNCIONARIO, EnumSet.of(AccessArea.ARMAZEM, AccessArea.GARAGEM));
    }

    public boolean haveAccess(Role role, AccessArea area) {
        return MAPA.getOrDefault(role, Set.of()).contains(area);
    }
}
