package server.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.data.entity.Role;
import server.data.repository.RoleRepository;
import server.utils.exception.notfound.RoleCustomNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleCustomNotFoundException("Role with Id: " + id + " not found."));
    }
}
