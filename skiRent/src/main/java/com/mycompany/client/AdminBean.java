import com.mycompany.entities.Users;
import com.mycompany.jaas.UserEJB;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;

@ManagedBean
@ViewScoped
public class AdminBean implements Serializable {
    @EJB
    private UserEJB userEJB;

    private List<Users> propietariosNoVerificados;
    private List<Users> todosPropietarios;

    @PostConstruct
    public void init() {
        cargarPropietariosNoVerificados();
        cargarTodosPropietarios();
    }

    public List<Users> getPropietariosNoVerificados() {
        return propietariosNoVerificados;
    }

    public List<Users> getTodosPropietarios() {
        return todosPropietarios;
    }

    public void cargarPropietariosNoVerificados() {
        propietariosNoVerificados = userEJB.obtenerPropietariosNoVerificados();
    }

    public void cargarTodosPropietarios() {
        todosPropietarios = userEJB.obtenerTodosPropietarios();
    }

    public void verificarPropietario(String email) {
        userEJB.actualizarVerificacionUsuario(email, true);
        cargarPropietariosNoVerificados(); // Recargar la lista después de la verificación
    }

    public void eliminarPropietario(String email) {
        userEJB.eliminarUsuarioPorEmail(email);
        cargarTodosPropietarios(); // Recargar la lista después de la eliminación
    }
    
    public void autorizarUsuario(String email) {
        userEJB.actualizarVerificacionUsuario(email, true);
        cargarPropietariosNoVerificados(); // Recargar la lista después de la autorización
    }
}
