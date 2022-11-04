package ciclo4.grupo37.misiontic.org.MainSecurity.controladores;

import ciclo4.grupo37.misiontic.org.MainSecurity.modelos.Permiso;
import ciclo4.grupo37.misiontic.org.MainSecurity.modelos.PermisosRol;
import ciclo4.grupo37.misiontic.org.MainSecurity.modelos.Rol;
import ciclo4.grupo37.misiontic.org.MainSecurity.repositorios.RepositorioPermiso;
import ciclo4.grupo37.misiontic.org.MainSecurity.repositorios.RepositorioPermisosRoles;
import ciclo4.grupo37.misiontic.org.MainSecurity.repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisosRoles {
    @Autowired
    private RepositorioPermisosRoles miRepositorioPermisosRoles;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<PermisosRol> index(){
        return miRepositorioPermisosRoles.findAll();
    }
    /**
     * Asignación de rol y permiso
     * @param id_rol
     * @param id_permiso
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRol create(@PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRol  nuevoPermisoRol = new PermisosRol();
        Rol elRol = miRepositorioRol.findById(id_rol).orElse(null);
        Permiso elPermiso = miRepositorioPermiso.findById(id_permiso).orElse(null);
        if(elRol!=null && elPermiso!= null){
            nuevoPermisoRol.setPermiso(elPermiso);
            nuevoPermisoRol.setRol(elRol);
            return miRepositorioPermisosRoles.save(nuevoPermisoRol);
        }else{
            return null;
        }
    }

    @GetMapping("{id}")
    public PermisosRol show(@PathVariable String id){
        PermisosRol permisosRolActual = miRepositorioPermisosRoles
                .findById(id)
                .orElse(null);
        return permisosRolActual;
    }

    /**
     * Modificaciones ROL y PERMISO
     * @param id_permisoRol
     * @param id_rol
     * @param id_permiso
     * @return PermisosRol
     */

    @PutMapping("{id_permisoRol}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRol update(@PathVariable String id_permisoRol,
                              @PathVariable String id_rol,
                              @PathVariable String id_permiso){
        PermisosRol permisosRolesActuales = miRepositorioPermisosRoles
                .findById(id_permisoRol)
                .orElse(null);
        Rol elRol = miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso = miRepositorioPermiso.findById(id_permiso).get();
        if(permisosRolesActuales!=null && elPermiso != null && elRol != null){
            permisosRolesActuales.setPermiso(elPermiso);
            permisosRolesActuales.setRol(elRol);
            return miRepositorioPermisosRoles.save(permisosRolesActuales);
        }else {
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        PermisosRol permisosRolActual = miRepositorioPermisosRoles
                .findById(id)
                .orElse(null);
        if(permisosRolActual!=null) {
            miRepositorioPermisosRoles.delete(permisosRolActual);
        }
    }

    @GetMapping("validar-permiso/rol/{id_rol}")
    public PermisosRol getPermiso(@PathVariable String id_rol,
                                  @RequestBody Permiso infoPermiso) {
        Permiso elPermiso = miRepositorioPermiso
                .getPermiso(infoPermiso.getUrl(),
                        infoPermiso.getMetodo()
                );
        Rol elRol = miRepositorioRol.findById(id_rol).get();
        if (elPermiso!=null && elRol!= null){
            return miRepositorioPermisosRoles.getPermisoRol(elRol.get_id(),
                    elPermiso.get_id());
        }else{
            return null;
        }
    }
}
