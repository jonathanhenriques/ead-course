package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
//define a nao serializacao de parametros null no Json
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "EAD_TB_MODULES")
public class ModuleModel implements Serializable {

    private static final long serialVersionUID = 1;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID moduleId;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false, length = 250)
    private String description;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    //Nao mostra o campo no GET, apenas em escrita como POST, PUT...
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //optional = define que tem que atrelar um course a um module
    //fetch = FetchType.LAZY = nao traz o valor do BD por padrao, necessaria query no repositoriy
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CourseModel course;

    //Nao mostra o campo no GET, apenas em escrita como POST, PUT...
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //fetch = FetchType.LAZY = nao traz o valor do BD por padrao, necessaria query no repositoriy
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    //FetchMode.SUBSELECT = no repository sera feita uma consulta para course e mais uma para trazer todos os modules relacionados
    @Fetch(FetchMode.SUBSELECT)
    private Set<LessonModel> lessons;


}
