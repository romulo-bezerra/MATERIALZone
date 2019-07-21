//package br.edu.ifpb.tccii.materialzone.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//@Document(indexName = "materiais", type = "material")
//public class Material {
//
//    @Id
//    private String id;
//    private String linkRepositorio;
//    private String titulo;
//    private String categoria;
//    private String descricao;
//    private ZonedDateTime timeStamp;
//    private String informacaoExtra;
//    private List<Comentario> comentarios;
//    private List<Avaliacao> avaliacoes;
//    private List<String> linhasArquivoRepo;
//
//    public Material(){
//        this.comentarios = new ArrayList<>();
//        this.avaliacoes = new ArrayList<>();
//        this.linhasArquivoRepo = new ArrayList<>();
//    }
//
//}
