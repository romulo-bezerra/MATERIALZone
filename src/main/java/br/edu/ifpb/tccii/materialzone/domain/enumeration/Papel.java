package br.edu.ifpb.tccii.materialzone.domain.enumeration;

public enum Papel {

    PROFESSOR("Professor"),
    ALUNO("Aluno");

    private final String label;

    Papel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
