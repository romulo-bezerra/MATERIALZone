package br.edu.ifpb.tccii.materialzone.domain.enumeration;

public enum Sexo {

    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro");

    private final String label;

    Sexo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
