package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.RoleService;
import br.edu.ifpb.tccii.materialzone.domain.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "RoleResource Controller", description = "Serviços pertinentes à Roles")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Role";

    @Autowired private RoleService roleService;

    public RoleResource() { }

    @GetMapping("/role")
    @ApiOperation(value = "Recupera todos as roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        log.debug("REST request to get all Roles");
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Role> rolesPag = roleService.findAll(pageRequest);
        return ResponseEntity.ok().body(rolesPag.getContent());
    }

}
