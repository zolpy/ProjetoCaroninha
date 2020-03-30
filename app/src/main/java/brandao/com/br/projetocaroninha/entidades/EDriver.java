package brandao.com.br.projetocaroninha.entidades;

import com.google.firebase.database.DatabaseReference;

import brandao.com.br.projetocaroninha.config.ConfigFirebase;

public class EDriver {

    private String email,password,name,phone,idDriver;
    private  boolean driver;

    public EDriver() {
    }

    public EDriver(String email, String password, String name, String phone, String idDriver, Boolean driver) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.idDriver = idDriver;
        this.driver = driver;
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("users").child("drivers").child(String.valueOf(getIdDriver())).child("infoCadastro2").setValue(this);
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }
}
