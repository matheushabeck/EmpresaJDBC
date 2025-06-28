package com.empresa;

public class Funcionario extends Pessoa {
    private String matricula;   // Formato "F" + 3 números, ex: F007
    private String departamento;

    public Funcionario() {}

    public Funcionario(int id, String nome, String email, String matricula, String departamento) {
        super(id, nome, email);
        this.matricula = matricula;
        this.departamento = departamento;
    }

    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
