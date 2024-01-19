
package pt.uevora;

import repository.Repositorio;

public class Empresa {
    
    
    private String nome;
    private String responsavel;
    private String localidade;
    private String descricao;

    public Empresa(String nome, String responsavel, String localidade, String descricao) {
        this.nome = nome;
        this.responsavel = responsavel;
        this.localidade = localidade;
        this.descricao = descricao;
        
    }
    
    // Inscreve a empresa no sistema (visto não ser necessário confirmações de propostas) aqui também se aplica
    // Simplesmente adiciona a empresa ao repositorio
    public void inscreverEmpresa(Repositorio r){
        r.addEmpresa(this);
    }

    // Submete o estágio para o repositório, que o adiciona na BD
    public void subEstagio(Estagio e, Repositorio r){
        // Define a empresa como representate do estágio e adiciona ao repositório
        e.setE(this);
        r.addEstagio(e);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
