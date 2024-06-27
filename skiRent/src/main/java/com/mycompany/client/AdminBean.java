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

    @PostConstruct
    public void init() {
        cargarPropietariosNoVerificados();
    }

    public List<Users> getPropietariosNoVerificados() {
        return propietariosNoVerificados;
    }

    public void cargarPropietariosNoVerificados() {
        propietariosNoVerificados = userEJB.obtenerPropietariosNoVerificados();
        System.out.println("Propietarios no verificados: " + propietariosNoVerificados.size());
        for (Users propietario : propietariosNoVerificados) {
            System.out.println("Propietario: " + propietario.getEmail());
        }
    }

    public void autorizarUsuario(String email) {
        userEJB.actualizarVerificacionUsuario(email, true);
        cargarPropietariosNoVerificados(); // Recargar la lista después de la autorización
    }
}
