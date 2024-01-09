package com.ead.course.models;


import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
//define a nao serializacao de parametros null no Json
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "EAD_TB_COURSES")
public class CourseModel implements Serializable {

    private static final long serialVersionUID = 1;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;
    @Column(nullable = false, length = 150)
    private String name;
    @Column(nullable = false, length = 250)
    private String description;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    //    retorna a data no formato esperado
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    //    retorna a data no formato esperado
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;
    @Column(nullable = false)
    private UUID userInstructor;

    //Nao mostra o campo no GET, apenas em escrita como POST, PUT...
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //fetch = FetchType.LAZY = nao traz o valor do BD por padrao, necessaria query no repository
    //cascade = CascadeType.ALL = quando um curso for deletado, JPA vai deletar cada um dos modulos vinculados
    // orphanRemoval = true = todos os modulos sem vinculos com cursos sao removidos pela JPA automaticamente
//    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    //delegamos ao BD realizar a delecao de todos os modules sem vinculo com course, mais performatico
//    @OnDelete(action = OnDeleteAction.CASCADE)
    //FetchMode.JOIN = no repository sera feita uma consulta trazendo todos os courses e modules, ignorando o LAZY
    //FetchMode.SELECT = no repository sera feita uma consulta trazendo todos os courses e mais n para cada module
    //FetchMode.SUBSELECT = no repository sera feita uma consulta para course e mais uma para trazer todos os modules relacionados
    @Fetch(FetchMode.SUBSELECT)
    private Set<ModuleModel> modules;






}
