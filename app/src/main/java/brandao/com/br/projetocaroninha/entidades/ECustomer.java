package brandao.com.br.projetocaroninha.entidades;

import com.google.firebase.database.DatabaseReference;

import brandao.com.br.projetocaroninha.config.ConfigFirebase;

public class ECustomer {
    private String email,password,phone,name,idCustomer,cpf;
    private boolean customer;

    public ECustomer() {
    }


    public ECustomer(String email, String password, String phone, String name, String idCustomer, String cpf, Boolean customer) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.idCustomer = idCustomer;
        this.customer = customer;
        this.cpf = cpf;
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("users").child("customers").child(String.valueOf(getIdCustomer())).child("infoCadastro2").setValue(this);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }
}
