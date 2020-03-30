package brandao.com.br.projetocaroninha.entidades;

import com.google.firebase.database.DatabaseReference;

import brandao.com.br.projetocaroninha.config.ConfigFirebase;

public class Usuarios {
    private String email,senha,id;
    private String motorista,cpfMotorista,phoneMotorista,nomeMotorista,cidadeMotorista,estadoMotorista,carroMotorista;
    private String caroneira,cpfCaroneira,phoneCaroneira,nomeCaroneira,cidadeCaroneira,estadoCaroneira,carroCaroneira;
    private String userCaroninha,cpf,telefone,nome,cidade,estado,carro;
    private Boolean situacaoDaLicenca;


    public Usuarios() {
    }

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfigFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child(String.valueOf(getId())).child("infoCadastro").setValue(this);
        referenciaFirebase.child("usuarios").child(String.valueOf(getId())).child("infoReCadastro").setValue(this);
    }

    public String getUserCaroninha() {
        return userCaroninha;
    }

    public void setUserCaroninha(String userCaroninha) {
        this.userCaroninha = userCaroninha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCarro() {
        return carro;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSituacaoDaLicenca() {
        return situacaoDaLicenca;
    }

    public void setSituacaoDaLicenca(Boolean situacaoDaLicenca) {
        this.situacaoDaLicenca = situacaoDaLicenca;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public String getPhoneMotorista() {
        return phoneMotorista;
    }

    public void setPhoneMotorista(String phoneMotorista) {
        this.phoneMotorista = phoneMotorista;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getCidadeMotorista() {
        return cidadeMotorista;
    }

    public void setCidadeMotorista(String cidadeMotorista) {
        this.cidadeMotorista = cidadeMotorista;
    }

    public String getEstadoMotorista() {
        return estadoMotorista;
    }

    public void setEstadoMotorista(String estadoMotorista) {
        this.estadoMotorista = estadoMotorista;
    }

    public String getCarroMotorista() {
        return carroMotorista;
    }

    public void setCarroMotorista(String carroMotorista) {
        this.carroMotorista = carroMotorista;
    }


    public String getCaroneira() {
        return caroneira;
    }

    public void setCaroneira(String caroneira) {
        this.caroneira = caroneira;
    }

    public String getCpfCaroneira() {
        return cpfCaroneira;
    }

    public void setCpfCaroneira(String cpfCaroneira) {
        this.cpfCaroneira = cpfCaroneira;
    }

    public String getPhoneCaroneira() {
        return phoneCaroneira;
    }

    public void setPhoneCaroneira(String phoneCaroneira) {
        this.phoneCaroneira = phoneCaroneira;
    }

    public String getNomeCaroneira() {
        return nomeCaroneira;
    }

    public void setNomeCaroneira(String nomeCaroneira) {
        this.nomeCaroneira = nomeCaroneira;
    }

    public String getCidadeCaroneira() {
        return cidadeCaroneira;
    }

    public void setCidadeCaroneira(String cidadeCaroneira) {
        this.cidadeCaroneira = cidadeCaroneira;
    }

    public String getEstadoCaroneira() {
        return estadoCaroneira;
    }

    public void setEstadoCaroneira(String estadoCaroneira) {
        this.estadoCaroneira = estadoCaroneira;
    }

    public String getCarroCaroneira() {
        return carroCaroneira;
    }

    public void setCarroCaroneira(String carroCaroneira) {
        this.carroCaroneira = carroCaroneira;
    }


}

